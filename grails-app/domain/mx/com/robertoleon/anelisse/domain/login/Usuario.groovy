package mx.com.robertoleon.anelisse.domain.login

class Usuario {

    transient springSecurityService
    transient String captcha

    String username
    String password
    boolean enabled
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired

    String nombre
    String apellidoPaterno
    String apellidoMaterno
    String puesto
    String telefono
    String observaciones
    Integer usuarioAlta
    Integer usuarioModifica
    Date fechaAlta
    Date fechaModifica

    static constraints = {
        username (blank:false,unique:true,maxSize:60,email:true)
        password (blank:false,maxSize:50,unique:true)
        nombre(blank:false,maxSize:50)
        apellidoPaterno(blank:false,maxSize:50)
        apellidoMaterno(blank:false,maxSize:50)
        puesto(blank:false,maxSize:25)
        telefono(maxSize:25,matches:'[\\d]+')
        observaciones(nullable:false,maxSize:50)
        enabled(nullable:false)
        usuarioAlta(nullable:false)
        usuarioModifica(nullable:true)
        fechaAlta(nullable:false)
        fechaModifica(nullable:true)
    }

    static mapping = {
        password column: '`password`'
    }

    Set<Permiso> getAuthorities() {
        UsuarioPermiso.findAllByUsuario(this).collect { it.permiso } as Set
    }

    def beforeInsert() {
        encodePassword()
    }

    def beforeUpdate() {
        if (isDirty('password')) {
            encodePassword()
        }
    }



    protected void encodePassword() {
        password = springSecurityService.encodePassword(password)
    }
}