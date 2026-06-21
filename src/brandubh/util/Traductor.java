package brandubh.util;

/**
 * Clase que proporciona métodos para traducir entre notación algebraica y coordenadas en el juego Brandubh.
 * @author Arkaitz Díez y Hector Sánchez
 */
public class Traductor {
    /**
     * Constructor por defecto de la clase Traductor.
     * Este constructor no realiza ninguna inicialización específica.
     */
    public Traductor() {
       
    }

    /**
     * Convierte una cadena de texto en notación algebraica a una coordenada en el tablero.
     * 
     * @param texto La cadena de texto en notación algebraica (por ejemplo, "a1").
     * @return La coordenada correspondiente en el tablero, o null si la entrada es inválida.
     */
    public static Coordenada consultarCoordenadaParaNotacionAlgebraica(String texto) {
        // Verificar si el texto tiene longitud 2
        if (texto.length() != 2) {
            return null;
        }

        // Obtener los caracteres individuales
        char letra = texto.charAt(0);
        char numero = texto.charAt(1);

        // Verificar si la letra es válida
        if (letra < 'a' || letra > 'g') {
            return null;
        }

        // Verificar si el número es un dígito válido para tu tablero
        int numeroValor = Character.getNumericValue(numero);
        if (numeroValor < 1 || numeroValor > 7) {
            return null;
        }

        // Convertir la letra a una columna numérica
        int columna = letra - 'a';

        // Convertir el número a una fila numérica
        int fila = 7 - numeroValor;

        // Crear y devolver una nueva instancia de Coordenada
        return new Coordenada(fila, columna);
    }

    /**
     * Convierte una coordenada en el tablero a una cadena de texto en notación algebraica.
     * 
     * @param coordenada La coordenada en el tablero.
     * @return La cadena de texto en notación algebraica correspondiente, o null si la coordenada es inválida.
     */
    public static String consultarTextoEnNotacionAlgebraica(Coordenada coordenada) {
        // Verificar si la coordenada es válida
        if (coordenada == null || coordenada.fila() < 0 || coordenada.fila() >= 7 ||
            coordenada.columna() < 0 || coordenada.columna() >= 7) {
            return null;
        }

        // Convertir la fila a la letra correspondiente
        char letra = (char) ('a' + coordenada.columna());

        // Convertir la columna a un número de 1 a 7
        int numero = 7 - coordenada.fila();

        // Crear y devolver el texto en notación algebraica
        return Character.toString(letra) + Integer.toString(numero);
    }

    /**
     * Verifica si una cadena de texto es correcta para representar una coordenada en notación algebraica.
     * 
     * @param texto La cadena de texto a verificar.
     * @return true si la cadena es válida para representar una coordenada, false de lo contrario.
     */
    public static boolean esTextoCorrectoParaCoordenada(String texto) {
        if (texto.length() == 2) {
            char primerCaracter = texto.charAt(0);
            char segundoCaracter = texto.charAt(1);
            if (Character.isLetter(primerCaracter) && Character.isDigit(segundoCaracter)) {
                int segundoCaracterNumero = Character.getNumericValue(segundoCaracter);
                if (primerCaracter < 'a' || primerCaracter > 'g') {
                    return false;
                } else if (segundoCaracterNumero < 1 || segundoCaracterNumero > 7) {
                    return false;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
