package mx.com.robertoleon.anelisse.domain.blog

import mx.com.robertoleon.anelisse.domain.login.Usuario

class BlogComentario {


     int votos;
     Usuario usuario;
     BlogComentario respuestaA;
     Date fecha;
     String comentario;
     Set<BlogComentario> respuestas;

    static constraints = {
    }
}
