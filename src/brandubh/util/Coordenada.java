package brandubh.util;

/**
 * Representa las coordenadas de una posición en un tablero bidimensional.
 * Este registro (record) es inmutable y se utiliza para almacenar información sobre filas y columnas.
 *
 * @param fila La fila representada con un numero
 * @param columna La columna representada con un numero
 * @author Arkaitz y Hector
 */
public record Coordenada(int fila, int columna) {

    /**
     * Obtiene el valor de la fila de la coordenada.
     *
     * @return El valor de la fila.
     */
    public int fila() {
        return fila;
    }

    /**
     * Obtiene el valor de la columna de la coordenada.
     *
     * @return El valor de la columna.
     */
    public int columna() {
        return columna;
    }
}