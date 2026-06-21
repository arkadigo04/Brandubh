package brandubh.util;

/**
 * Enumera los tipos de piezas en el juego, cada una con un carácter representativo y un color asociado.
 * Las piezas incluyen Defensor (Blanco), Atacante (Negro) y Rey (Blanco).
 *
 * @author Arkaitz Díez y Hector Sánchez
 */
public enum TipoPieza {
    /**
     * Representa la pieza Defensor (Blanco) en el juego.
     */
    DEFENSOR('D', Color.BLANCO),

    /**
     * Representa la pieza Atacante (Negro) en el juego.
     */
    ATACANTE('A', Color.NEGRO),

    /**
     * Representa la pieza Rey (Blanco) en el juego.
     */
    REY('R', Color.BLANCO);

	/**
	 * Representa el carácter asociado a la pieza.
	 */
	private char caracter;

	/**
	 * Representa el color asociado la pieza.
	 */
	private Color color;

    /**
     * Construye un tipo de pieza con el carácter y el color especificados.
     *
     * @param caracter El carácter representativo de la pieza.
     * @param color    El color asociado a la pieza.
     */
    private TipoPieza(char caracter, Color color) {
        this.caracter = caracter;
        this.color = color;
    }

    /**
     * Obtiene el carácter representativo de la pieza.
     *
     * @return El carácter representativo.
     */
    public char toChar() {
        return caracter;
    }

    /**
     * Consulta el color asociado a la pieza.
     *
     * @return El color de la pieza (BLANCO o NEGRO).
     */
    public Color consultarColor() {
        return color;
    }
}