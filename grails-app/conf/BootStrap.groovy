import mx.com.robertoleon.anelisse.domain.blog.BlogPost
import mx.com.robertoleon.anelisse.domain.login.Permiso
import mx.com.robertoleon.anelisse.domain.login.Usuario
import mx.com.robertoleon.anelisse.domain.login.UsuarioPermiso

class BootStrap {

    def springSecurityService

    def init = { servletContext ->
        //log.info "Cargando directorio de archivos para BootStrap $archivosBootStrap"
        if (!Permiso.count()){
            try{


            def rolAdmin = new Permiso(authority: 'ROLE_ADMIN').save(failOnError: true)
            def rolSeguros = new Permiso(authority: 'ROLE_SEGUROS').save(failOnError: true)
            def rolPrestaciones = new Permiso(authority: 'ROLE_PRESTACIONES').save(failOnError: true)
            def rolResponsableNomina = new Permiso(authority: 'ROLE_RESPONSABLE_NOMINA').save(failOnError: true)
            def rolControlAsistencia = new Permiso(authority: 'ROLE_CONTROL_ASISTENCIA').save(failOnError: true)
            def rolPrestamos = new Permiso(authority: 'ROLE_PRESTAMOS').save(failOnError: true)
            def rolRups = new Permiso(authority: 'ROLE_RUPS').save(failOnError: true)
            def rolRH = new Permiso(authority: 'ROLE_RH').save(failOnError: true)

            def testUser = new Usuario(
                    nombre:"J.R",
                    apellidoPaterno:"Le?n",
                    apellidoMaterno:"Cruz",
                    password:"admin",
                    puesto:"Java Sr.",
                    username:"admin@robertoleon.com.mx",
                    telefono:"4444444444",
                    observaciones:"ninguna",
                    enabled:true,
                    usuarioAlta:0,
                    fechaAlta:new Date(),
                    accountExpired:false,
                    accountLocked:false,
                    passwordExpired:false,
                    captcha:'').save(failOnError: true)

            UsuarioPermiso.create testUser, rolAdmin, true

            BlogPost post = new BlogPost(status:1, votos:0, usuario: testUser, titulo: "primer post",
                    texto: "texto del primer post escrito por un usuario de pruyeba en el bootstrap",
                    fecha: new Date(), comenta: 1)

            }catch (Exception e) {
                Usuario.withSession { session ->
                    session.clear()
                }
            }
        }
    }

    def destroy = {
    }
}
