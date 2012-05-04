package mx.com.robertoleon.anelisse.domain.login

import org.apache.commons.lang.builder.HashCodeBuilder

class UsuarioPermiso implements Serializable{

    Usuario usuario
    Permiso permiso

    boolean equals(other) {
        if (!(other instanceof UsuarioPermiso)) {
            return false
        }

        other?.id == usuario?.id
    }

    int hashCode() {
        def builder = new HashCodeBuilder()
        if (usuario) builder.append(usuario.id)
        builder.toHashCode()
    }

    static UsuarioPermiso get(long usuarioId, long permisoId) {
        find 'from UsuarioPermiso where usuario.id=:usuarioId and permiso.id=:permisoId',
                [usuarioId: usuarioId, permisoId: permisoId]
    }

    static UsuarioPermiso get(long usuarioId) {
        find 'from UsuarioPermiso where usuario.id=:usuarioId',
                [usuarioId: usuarioId]
    }

    static List<UsuarioPermiso> findAllByEstadoLog(int max, int offset, boolean estadoLog) {
        findAll("from UsuarioPermiso where usuario.enabled=?",
                [estadoLog],[max:max, offset:offset])
    }

    static UsuarioPermiso create(UsuarioPermiso usuario, Permiso permiso, boolean flush = false) {
        new UsuarioPermiso(usuario: usuario, permiso: permiso).save(flush: flush, insert: true)
    }

    static boolean remove(Usuario usuario, Permiso permiso, boolean flush = false) {
        UsuarioPermiso instance = UsuarioPermiso.findByUsuarioAndPermiso(usuario, permiso)
        if (!instance) {
            return false
        }

        instance.delete(flush: flush)
        true
    }

    static void removeAll(Usuario usuario) {
        executeUpdate 'DELETE FROM UsuarioPermiso WHERE usuario=:usuario', [usuario: usuario]
    }

    static void removeAll(Permiso permiso) {
        executeUpdate 'DELETE FROM UsuarioPermiso WHERE permiso=:permiso', [permiso: permiso]
    }

    static mapping = {
        id composite: ['permiso', 'usuario']
        version false
    }

}