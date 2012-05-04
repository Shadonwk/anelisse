package mx.com.robertoleon.anelisse.domain.login

class Permiso {

    String authority

    static mapping = {
        cache true
    }

    static constraints = {
        authority blank: false, unique: true
    }
}
