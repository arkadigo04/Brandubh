package brandubh.modelo;

import brandubh.util.Coordenada;
import brandubh.util.TipoCelda;

import java.util.Objects;

import brandubh.util.Color;
/**
 * La clase "Celda" representa una casilla en un tablero de juego,
 * con atributos que incluyen coordenada, tipo de celda (normal, trono, provincia)
 * y una posible pieza. Ofrece métodos para clonar, colocar, consultar y manipular
 * piezas en la celda, así como comparaciones y representaciones textuales.
 * @author Arkaitz Diez y Hector Sanchez
 */
public class Celda {

	/**
     * La coordenada de la celda en el tablero.
     */
    private Coordenada coordenada;

    /**
     * El tipo de la celda (NORMAL, TRONO, PROVINCIA).
     */
    private TipoCelda tipoCelda;

    /**
     * Una pieza (si existe) en la celda.
     */
    private Pieza pieza;

	
    /**
     * Crea una celda con una coordenada y tipo de celda NORMAL por defecto.
     * @param coordenada La coordenada de la celda.
     */
    public Celda(Coordenada coordenada) {
        this.coordenada = coordenada; // Asigna la coordenada proporcionada.
        this.tipoCelda = TipoCelda.NORMAL; // Establece el tipo de celda como NORMAL por defecto.
    }

    /**
     * Crea una celda con una coordenada y un tipo de celda específico.
     * @param coordenada La coordenada de la celda.
     * @param tipoCelda  El tipo de celda (NORMAL, TRONO, PROVINCIA).
     */
    public Celda(Coordenada coordenada, TipoCelda tipoCelda) {
        this.coordenada = coordenada; // Asigna la coordenada proporcionada.
        this.tipoCelda = tipoCelda; // Establece el tipo de celda proporcionado.
    }
	
	//Metodos
	
    /**
     * Crea y devuelve una copia de la celda actual.
     * @return Una nueva instancia de Celda con los mismos atributos que la celda original.
     */
    public Celda clonar() {
        Celda celdaClonada = new Celda(this.coordenada, this.tipoCelda); // Crea una nueva celda con la misma coordenada y tipo.
        if (!estaVacia()) {
            celdaClonada.colocar(this.pieza.clonar()); // Si hay una pieza, clona la pieza y la coloca en la celda clonada.
        }
        
        return celdaClonada; // Devuelve la celda clonada.
    }

    /**
     * Coloca una pieza en la celda.
     * @param pieza La pieza a colocar en la celda.
     */
    public void colocar(Pieza pieza) {
        this.pieza = pieza; // Asigna la pieza proporcionada a la celda.
    }
	
    /**
     * Consulta el color de la pieza en la celda.
     * @return El color de la pieza si existe, o null si la celda está vacía o la pieza es nula.
     */
    public Color consultarColorDePieza() {
        if (estaVacia()) {
            return null; // Devuelve null si la celda está vacía o la pieza es nula
        }
        return pieza.consultarColor(); // Devuelve el color de la pieza.
    }

    /**
     * Consulta la coordenada de la celda.
     * @return La coordenada de la celda.
     */
    public Coordenada consultarCoordenada() {
        return this.coordenada; // Devuelve la coordenada de la celda.
    }
	
    /**
     * Consulta la pieza en la celda.
     * @return La pieza en la celda, o null si la celda está vacía.
     */
    public Pieza consultarPieza() {
        return this.pieza; // Devuelve la pieza en la celda.
    }

    /**
     * Consulta el tipo de celda.
     * @return El tipo de celda de esta instancia.
     */
    public TipoCelda consultarTipoCelda() {
        return this.tipoCelda; // Devuelve el tipo de celda de esta instancia.
    }

    /**
     * Elimina la pieza actual de la celda si no está vacía.
     */
    public void eliminarPieza() {
        if (!estaVacia()) { // Verifica si la celda no está vacía.
            this.pieza = null; // Elimina la referencia a la pieza en la celda.
        }
    }

	
    /**
     * Verifica si la celda está vacía (sin pieza).
     * @return true si la celda está vacía, false si contiene una pieza.
     */
    public boolean estaVacia() {
        return this.pieza == null; // Devuelve true si no hay una pieza en la celda, false en caso contrario.
    }

    /**
     * Calcula el código hash de la celda basado en sus atributos.
     * @return El código hash de la celda.
     */
    @Override
    public int hashCode() {
        return Objects.hash(coordenada, pieza, tipoCelda); // Calcula el código hash basado en los atributos.
    }

    /**
     * Compara si la celda es igual a otro objeto.
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
        Celda other = (Celda) obj;
        return Objects.equals(coordenada, other.coordenada) && Objects.equals(pieza, other.pieza)
                && tipoCelda == other.tipoCelda; // Compara los atributos para verificar si las celdas son iguales.
    }

    /**
     * Representación en cadena de la celda y sus atributos.
     * @return Una cadena que representa los atributos de la celda.
     */
    @Override
    public String toString() {
        return "Celda [coordenada=" + coordenada + ", tipoCelda=" + tipoCelda + ", pieza=" + pieza + "]";
    }}
	
	
	
