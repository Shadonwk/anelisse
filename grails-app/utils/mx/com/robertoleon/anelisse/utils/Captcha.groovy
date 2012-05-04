package mx.com.robertoleon.anelisse.utils

/**
 * Created by IntelliJ IDEA.
 * Usuario: Shadonwk
 * Date: 24/04/12
 * Time: 05:33 PM
 */
class Captcha {
    def CadenaAleatoria() {
        String str=new  String("QAa0bcLdUK2eHfJgTP8XhiFj61DOklNm9nBoI5pGqYVrs3CtSuMZvwWx4yE7zR");
        StringBuilder sb=new StringBuilder();
        Random r = new Random();
        int te=0;
        for(int i=1;i<=6;i++){
            te=r.nextInt(62);
            sb.append(str.charAt(te));
        }
        return sb.toString();
    }
}
