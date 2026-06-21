package brandubh.modelo;

import brandubh.util.TipoPieza;

import java.util.Objects;

import brandubh.util.Color;
/**
 * @author Arkaitz Diez y Hector Sanchez
 */
/**
 * Representa una pieza en el juego.
 */
public class Pieza {

	/**
	 * Representa el tipo de una pieza
	 */
    private TipoPieza tipoPieza;

    /**
     * Constructor que inicializa la pieza con un tipo específico.
     * @param tipoPieza El tipo de la pieza a crear.
     */
    public Pieza(TipoPieza tipoPieza) {
        this.tipoPieza = tipoPieza; // Establece el tipo de pieza proporcionado.
    }

    /**
     * Consulta el color asociado a la pieza.
     * @return El color de la pieza.
     */
    public Color consultarColor(){
        return tipoPieza.consultarColor(); // Devuelve el color asociado al tipo de la pieza.
    }
	
    /**
     * Consulta el tipo de pieza.
     * @return El tipo de pieza de esta instancia.
     */
    public TipoPieza consultarTipoPieza() {
        return tipoPieza; // Devuelve el tipo de pieza de esta instancia.
    }

    /**
     * Crea y devuelve una copia de la pieza actual.
     * @return Una nueva instancia de Pieza con el mismo tipo de la pieza original.
     */
    public Pieza clonar() {
        Pieza pieza = new Pieza(tipoPieza); // Crea una nueva pieza con el mismo tipo.
        return pieza; // Devuelve la nueva instancia de Pieza.
    }

    /**
     * Calcula el código hash de la pieza basado en su tipo.
     * @return El código hash de la pieza.
     */
    @Override
    public int hashCode() {
        return Objects.hash(tipoPieza); // Calcula el código hash basado en el tipo de la pieza.
    }

    /**
     * Compara si la pieza es igual a otro objeto.
     * @param obj El objeto a comparar.
     * @return true si son iguales, false si son diferentes o el objeto es nulo o de una clase distinta.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pieza other = (Pieza) obj;
        return tipoPieza == other.tipoPieza; // Compara el tipo de la pieza para verificar si son iguales.
    }

    /**
     * Representación en cadena de la pieza y su tipo.
     * @return Una cadena que representa el tipo de la pieza.
     */
    @Override
    public String toString() {
        return "Pieza [tipoPieza=" + tipoPieza + "]"; // Devuelve una cadena que representa el tipo de la pieza.
    }

	
	
	
	
		
}
