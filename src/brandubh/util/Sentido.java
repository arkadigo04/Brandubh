package brandubh.util;

/**
 * Enumera las posibles direcciones (vertical y horizontal) con los desplazamientos correspondientes en filas y columnas.
 * Cada dirección tiene un desplazamiento específico en filas y columnas.
 *
 * @author Arkaitz Díez y Hector Sánchez
 */
public enum Sentido {
    /**
     * Representa la dirección norte en el eje vertical.
     */
    VERTICAL_N(-1, +0),

    /**
     * Representa la dirección sur en el eje vertical.
     */
    VERTICAL_S(+1, +0),

    /**
     * Representa la dirección este en el eje horizontal.
     */
    HORIZONTAL_E(+0, +1),

    /**
     * Representa la dirección oeste en el eje horizontal.
     */
    HORIZONTAL_O(+0, -1);

	/**
	 * Representa el desplazamiento en filas.
	 */
	private int desplazamientoEnFilas;

	/**
	 * Representa el desplazamiento en columnas.
	 */
	private int desplazamientoEnColumnas;


    /**
     * Construye un Sentido con los desplazamientos especificados en filas y columnas.
     *
     * @param despF El desplazamiento en filas.
     * @param despC El desplazamiento en columnas.
     */
    private Sentido(int despF, int despC) {
        this.desplazamientoEnColumnas = despC;
        this.desplazamientoEnFilas = despF;
    }

    /**
     * Obtiene el desplazamiento en columnas para esta dirección.
     *
     * @return El desplazamiento en columnas.
     */
    public int consultarDesplazamientoEnColumnas() {
        return this.desplazamientoEnColumnas;
    }

    /**
     * Obtiene el desplazamiento en filas para esta dirección.
     *
     * @return El desplazamiento en filas.
     */
    public int consultarDesplazamientoEnFilas() {
        return this.desplazamientoEnFilas;
    }
}