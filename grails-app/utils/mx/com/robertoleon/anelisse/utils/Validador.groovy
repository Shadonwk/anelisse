package mx.com.robertoleon.anelisse.utils

import java.util.regex.Pattern
import java.util.regex.Matcher

/**
 * Created by IntelliJ IDEA.
 * Usuario: Shadonwk
 * Date: 24/04/12
 * Time: 05:42 PM
 */
class Validador {
    String isValidaContrasenia(String contrasenia) {
        String mensajeError = null
        if (contrasenia.size() >= 8) {
            Pattern patron = Pattern.compile("(.*\\d){2}");
            Matcher encaja = patron.matcher(contrasenia);
            if (encaja.find()) {
                patron = Pattern.compile("(.*[A-Z]){1}");
                encaja = patron.matcher(contrasenia);
                boolean isMatch = encaja.find()
                if (!isMatch) {
                    mensajeError = "La contrase?a debe incluir al menos 1 letra may?scula"
                }
            } else {
                mensajeError = "La contrase?a debe incluir al menos 2 n?meros"
            }
        } else {
            mensajeError = "La contrase?a debe tener un m?nimo de 8 caracteres"
        }
        return mensajeError
    }
}
