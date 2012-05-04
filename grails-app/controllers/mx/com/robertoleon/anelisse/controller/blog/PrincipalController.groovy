package mx.com.robertoleon.anelisse.controller.blog

class PrincipalController {

    def index() {
        redirect action: 'principal'
    }

    /**
     * Show the login page.
     */
    def principal = {


        String view = 'Principal'

        render view: view
    }
}
