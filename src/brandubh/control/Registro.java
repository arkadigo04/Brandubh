package brandubh.control;

import brandubh.modelo.Jugada;
import brandubh.modelo.Tablero;

/**
 * La clase Registro representa un registro inmutable que contiene un objeto Tablero y una Jugada.
 * Se utiliza para almacenar estados del juego en el historial.
 * 
 * @param tablero El tablero asociado al registro.
 * @param jugada La jugada asociada al registro.
 
 */
public record Registro(Tablero tablero, Jugada jugada) {
    
    /**
     * Obtiene el tablero asociado al registro.
     * 
     * @return El tablero asociado al registro.
     */
    public Tablero tablero() {
        return tablero;
    }
    
    /**
     * Obtiene la jugada asociada al registro.
     * 
     * @return La jugada asociada al registro.
     */
    public Jugada jugada() {
        return jugada;
    }
}
