package mx.com.robertoleon.anelisse.utils

/**
 * Created by IntelliJ IDEA.
 * Usuario: Shadonwk
 * Date: 24/04/12
 * Time: 05:34 PM
 */
class CriptoUtils {
    private Validador validador = new Validador()

    String generarContrasenia(int lengthContrasenia){
        String str= new String("QAa0bcLdUK2eHfJgTP8XhiFj61DOklNm9nBoI5pGqYVrs3CtSuMZvwWx4yE7zR");
        StringBuilder contrasenia = new StringBuilder();
        Random rnd = new Random();
        int indexChar=0;
        for(int i=1; i<=lengthContrasenia; i++){
            indexChar=rnd.nextInt(62);
            contrasenia.append(str.charAt(indexChar));
        }
        return contrasenia.toString();
    }

    String getNuevaContrasenia() {
        String constrasenia = this.generarContrasenia(8)
        for(;this.validador.isValidaContrasenia(constrasenia);) {
            constrasenia = this.generarContrasenia(8)
        }
        return constrasenia
    }
}
