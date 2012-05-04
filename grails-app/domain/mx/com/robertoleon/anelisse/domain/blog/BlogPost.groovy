package mx.com.robertoleon.anelisse.domain.blog

import mx.com.robertoleon.anelisse.domain.login.Usuario

class BlogPost {


     int status;
     int votos;
     Usuario usuario;
     Date fecha;
     String titulo;
     String texto;
     Set<TagBlog> tags;
     Set<BlogComentario> comentarios;
     boolean comenta; //permite comentarios o no

    static constraints = {
    }
}
