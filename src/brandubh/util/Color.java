package brandubh.util;

/**
 * Enumeración que representa los colores en el juego Brandubh.
 * Cada color tiene una letra asociada ('B' para BLANCO y 'N' para NEGRO).
 * 
 * @author Arkaitz Díez y Hector Sánchez
 */
public enum Color {
    /**
     * Representa el color blanco en el juego, con la letra 'B'.
     */
    BLANCO('B'),

    /**
     * Representa el color negro en el juego, con la letra 'N'.
     */
    NEGRO('N');

	/**
	 * Representa la inicial para representar el color.
	 */
    private char letra;

    /**
     * Constructor privado para la enumeración Color.
     * 
     * @param letra La letra asociada al color.
     */
    private Color(char letra) {
        this.letra = letra;
    }

    /**
     * Obtiene la letra asociada al color.
     * 
     * @return La letra del color.
     */
    public char toChar() {
        return letra;
    }

    /**
     * Consulta el color contrario al actual.
     * 
     * @return El color contrario (BLANCO si es NEGRO y viceversa).
     */
    public Color consultarContrario() {
        // Se utiliza la expresión ternaria para una forma más concisa y legible.
        return this == BLANCO ? NEGRO : BLANCO;
    }
}
