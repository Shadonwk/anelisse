package mx.com.robertoleon.anelisse.controller.login


import grails.converters.JSON

import javax.servlet.http.HttpServletResponse


import mx.com.robertoleon.anelisse.domain.login.Usuario
import mx.com.robertoleon.anelisse.utils.Captcha
import mx.com.robertoleon.anelisse.utils.CriptoUtils
import mx.com.robertoleon.anelisse.utils.EmailUtils

import org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils
import org.springframework.security.authentication.AccountExpiredException
import org.springframework.security.authentication.CredentialsExpiredException
import org.springframework.security.authentication.DisabledException
import org.springframework.security.authentication.LockedException
import org.springframework.security.core.context.SecurityContextHolder as SCH
import org.springframework.security.web.WebAttributes
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.servlet.ModelAndView;

class LoginController {

    /**
     * Dependency injection for the authenticationTrustResolver.
     */
    def authenticationTrustResolver

    /**
     * Dependency injection for the springSecurityService.
     */
    def springSecurityService

    def loginService

    private EmailUtils emailUtils = new EmailUtils()

    private CriptoUtils criptoUtils = new CriptoUtils()

    /**
     * Default action; redirects to 'defaultTargetUrl' if logged in, /login/auth otherwise.
     */
    def index = {
        if (springSecurityService.isLoggedIn()) {
            redirect uri: '/principal/Principal'//SpringSecurityUtils.securityConfig.successHandler.defaultTargetUrl
        }
        else {
            redirect action: 'auth', params: params
        }
    }

    /**
     * Show the login page.
     */
    def auth = {

        def config = SpringSecurityUtils.securityConfig

        if (springSecurityService.isLoggedIn()) {
            //redirect uri: config.successHandler.defaultTargetUrl
            redirect uri: '/principal/Principal'

            return
        }
        String view = '../principal/Principal'
        String postUrl = "${request.contextPath}${config.apf.filterProcessesUrl}"
        render view: view, model: [postUrl: postUrl,
                rememberMeParameter: config.rememberMe.parameter]
    }

    /**
     * The redirect action for Ajax requests.
     */
    def authAjax = {
        println "location aca" + SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.setHeader 'Location', SpringSecurityUtils.securityConfig.auth.ajaxLoginFormUrl
        response.sendError HttpServletResponse.SC_UNAUTHORIZED
    }

    /**
     * Show denied page.
     */
    def denied = {
        if (springSecurityService.isLoggedIn() &&
                authenticationTrustResolver.isRememberMe(SCH.context?.authentication)) {
            // have cookie but the page is guarded with IS_AUTHENTICATED_FULLY
            redirect action: 'full', params: params
        }
    }

    /**
     * Login page for users with a remember-me cookie but accessing a IS_AUTHENTICATED_FULLY page.
     */
    def full = {
        def config = SpringSecurityUtils.securityConfig
        render view: '../principal/Principal', params: params,
                model: [hasCookie: authenticationTrustResolver.isRememberMe(SCH.context?.authentication),
                        postUrl: "${request.contextPath}${config.apf.filterProcessesUrl}"]
    }

    /**
     * Callback after a failed login. Redirects to the auth page with a warning message.
     */
    def authfail = {

        def correoElectronico = session[UsernamePasswordAuthenticationFilter.SPRING_SECURITY_LAST_USERNAME_KEY]
        String msg = ''
        def exception = session[WebAttributes.AUTHENTICATION_EXCEPTION]
        if (exception) {
            if (exception instanceof AccountExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.expired")
            }
            else if (exception instanceof CredentialsExpiredException) {
                msg = g.message(code: "springSecurity.errors.login.passwordExpired")
            }
            else if (exception instanceof DisabledException) {
                msg = g.message(code: "springSecurity.errors.login.disabled")
            }
            else if (exception instanceof LockedException) {
                msg = g.message(code: "springSecurity.errors.login.locked")
            }
            else {
                msg = g.message(code: "springSecurity.errors.login.fail")
            }
        }

        if (springSecurityService.isAjax(request)) {
            render([error: msg] as JSON)
        }
        else {
            flash.message = msg
            redirect action: 'auth', params: params
        }
    }

    /**
     * The Ajax success redirect url.
     */
    def ajaxSuccess = {
        render([success: true, username: springSecurityService.authentication.name] as JSON)
    }

    /**
     * The Ajax denied redirect url.
     */
    def ajaxDenied = {
        render([error: 'access denied'] as JSON)
    }

    def errorAcceso = {
        def model = [:]
        return new ModelAndView("/login/Recuperar", model)
    }

    def registro = {
        def model = [:]
        this.generateCaptcha()
        model +=[captcha:session.captcha]
        return new ModelAndView("/login/Registro", model)
    }



    def save = {
        def password = this.criptoUtils.getNuevaContrasenia()
        def usuario = new Usuario(params)
        usuario.observaciones = ""
        usuario.password = password
        usuario.usuarioAlta = 0
        usuario.fechaAlta = new Date()
        usuario.enabled = false
        def model = [:]

        // Validando errores generales
        def usuarioDuplicado = Usuario.findByUsernameAndEnabled(usuario?.username, true)
        usuario.validate()
        if (usuario.hasErrors()){
            model +=[usuarioInstance:usuario]
        } else {
            if (usuarioDuplicado == null && params.captcha == session.captcha) {
                log.info("Enviando notificacion a el usuario: ${usuario.username}")
                this.emailUtils.subject = "Registro de cuenta"
                this.emailUtils.from = "Registro de cuenta <info@robertoleon.com.mx>"
                this.emailUtils.contentHtml = this.getHtmlRegistro(usuario)
                this.emailUtils.recipients = ["${usuario.username}",'j.roberto.leon@gmail.com']
                this.emailUtils.sendEmail();

                log.info("Guardando el usuario: ${usuario.username}")
                usuario.save(flush:true)
                flash.message = "${message(code: 'ndirectorio.miembro.save', default: '', args:[usuario.username])}"
                session.captcha == ""
                return new ModelAndView("/login/RegistroExitoso", model)
            } else {
                model +=[usuarioInstance:usuario]
                if (params.captcha == session.captcha) {
                    request.messageError = "${message(code: 'directorio.error.email', default: 'Error', args:[usuario.username])}"
                } else {
                    request.messageErrorCaptcha = "${message(code: 'registgro.error.captcha', default: 'Codigo de verificaci&oacute;n invalido')}"
                    this.generateCaptcha()
                }
            }
        }

        return new ModelAndView("/login/Registro", model)
    }


    def recover = {
        if (params.email) {
            def model = [:]
            String password = this.criptoUtils.getNuevaContrasenia()
            def usuario = Usuario.findByUsername(params.email)
            if(usuario) {
                usuario.password = password
                usuario.save(flush:true)
                log.info("Enviando notificacion a el usuario: ${params.email}")
                this.emailUtils.subject = "Recuperaci?n de contrase?a N?mina"
                this.emailUtils.from = "Recuperaci?n de contrase?a N?mina <nomina@nomina.com>"
                this.emailUtils.contentHtml = this.getHtmlRecovery(usuario, password)
                this.emailUtils.recipients = ["${params.email}"]
                this.emailUtils.sendEmail();
                return new ModelAndView("/login/RecuperarExitoso", model)
            } else {
                request.messageError = "${message(code: 'recupera.error.contrasenia', default: 'Ingrese una direcci&oacute;n de correo electr&oacute;nico no valida')}"
                return new ModelAndView("/login/Recuperar", params)
            }
        } else {
            request.messageError = "${message(code: 'recupera.error.contrasenia', default: 'Ingrese una direcci&oacute;n de correo electr&oacute;nico no valida')}"
            return new ModelAndView("/login/Recuperar", params)
        }
    }

    def captcha = { render this.generateCaptcha() }

    def generateCaptcha() {

        session.captcha = new Captcha().CadenaAleatoria()
        session.captcha
    }
    private def getHtmlRecovery(usuario, password) {
        return """
			<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
			<html xmlns="http://www.w3.org/1999/xhtml">
			<head>
			<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
			<title>Recuperar Contrase?a</title>
			<style type="text/css">
			<!--
			body {
			font: Arial, "Lucida Grande", "Lucida Sans Unicode", Helvetica, Verdana, sans-serif
				margin-left: 50px;
				margin-right: 50px;
				background: transparent url("INAPESCA.png") no-repeat center center;
				background-attachment: fixed;
			}
			.style1 {font-size: 20}
			-->
			</style></head>
			<body>
			<div align="center">
			<a href="http://www.inapesca.gob.mx" target="_blank"><img src="banner3.png" width="75%"/></a></div><br/>
			<table align="center" width="75%">
			<tr>
			<td>
			<p><strong>Estimado usuario: ${usuario.nombre} ${usuario.apellidoPaterno} ${usuario.apellidoMaterno}</strong></p>
			</td>
			</tr>
			<tr>
			<td>
			<p align="justify">La cuenta de usuario con la que usted fue registrado en el <strong>S</strong>istema de <strong>N</strong>?nmina hace referencia a los siguientes datos:</p>
			</td>
			</tr>
			<tr>
			<td>
			<strong>Usuario:</strong> ${usuario.username}<br/>
			<strong>Contrase&ntilde;a:</strong> ${password}<br/>
			</td>
			</tr>
			<tr>
			<td>
			<p align="justify">Le solicitamos que respalde en un lugar seguro su usuario y contrase&ntilde;a ya que en caso de olvido o p&eacute;rdida de contrase&ntilde;a, el sistema podr&aacute; otorgarle una nueva contrase&ntilde;a ingresando el usuario con el que se registro; dicha contrase&ntilde;a ser&aacute; enviada al correo electr&oacute;nico <strong>${usuario.username}</strong> que nos proporciono cuando realizo su registro.</p>
			</td>
			</tr>
			<tr>
			<td>
			<p>Gracias por su inter&eacute;s en utilizar nuestro sistema.</p>
			</td>
			</tr>
			<tr>
			<td>
			<p><strong>Atentamente</strong>
			<br/>Administraci&oacte;n sistema <strong>N&oacte;mina</strong></p>
			</td>
			</tr>
			</table>
			<br/><br/><div align="center"><img src="footer.png" width="75%" /></div>

			</body>
			</html>
		"""
    }
    private def getHtmlRegistro(usuario) {
        return """
		<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
		<html xmlns="http://www.w3.org/1999/xhtml">
		<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>Registro de Usuario</title>
		<style type="text/css">
		<!--
		body {
		font: Arial, "Lucida Grande", "Lucida Sans Unicode", Helvetica, Verdana, sans-serif
			margin-left: 50px;
			margin-right: 50px;
			background: transparent url("INAPESCA.png") no-repeat center center;
			background-attachment: fixed;
		}
		.style1 {font-size: 20}
		-->
		</style></head>
		<body>
		<div align="center">
		<a href="http://www.robertoleon.com.mx/blog/" target="_blank"><img src=\"http://blog.servint.net/wp-content/uploads/2012/01/jelastic.jpg\" width="75%"></a></div> <br/>

		<table align="center" width="75%">
		<tr>
		<td>
		<strong>Estimado usuario: ${usuario.nombre} ${usuario.apellidoPaterno} ${usuario.apellidoMaterno}</strong>
		</td>
		</tr>
		<tr>
		<td>
		<p align="justify">Bienvenido al <strong>S</strong>istema  de <strong>B</strong>logs de robertoleon.com.mx/blog, por el momento su registro est&aacute; siendo aprobado por el administrador del sistema.</p>
		</td>
		</tr>
		<tr>
		<td>
		<p align="justify">Una vez que se haya aprobado dicha solicitud usted recibir&aacute; un usuario y una contrase?a con la cual podr&aacute; acceder al sistema la pr&oacute;xima vez.</p>
		</td>
		</tr>
		<tr>
		<td>
		<p>Le agradecemos su inter&eacute;s de registrarse en nuestro sistema.</p>
		</td>
		</tr>
		<tr>
		<td>
		<p><strong>Atentamente</strong><br/>Administraci&oacute;n del sistema <strong>Anelisse</strong></p>
		</td>
		</tr>
		</table>
		<br/>

		</body>
		</html>
	"""
    }
}
