package brandubh.control;

/**
 * La clase Historial almacena y gestiona un conjunto de registros que representan
 * los estados anteriores del juego.
 * 
 * @author Arkaitz Diez y Hector Sanchez
 */
public class Historial {
    /** Arreglo que almacena los registros de movimientos. */
    public Registro[] almacenamiento;
    
    /** Número actual de registros almacenados en el historial. */
    public int numeroRegistros;

    /**
     * Constructor de la clase Historial. Inicializa el arreglo de almacenamiento
     * con capacidad para 100 registros y establece el número de registros a 0.
     */
    public Historial() {
        this.almacenamiento = new Registro[100];
        numeroRegistros = 0;
    }

    /**
     * Añade un nuevo registro al final del historial.
     * 
     * @param registro El registro a añadir.
     */
    public void añadirUltimoRegistro(Registro registro) {
        almacenamiento[numeroRegistros] = registro;
        numeroRegistros++;
    }

    /**
     * Consulta el número actual de registros en el historial.
     * 
     * @return El número de registros almacenados.
     */
    public int consultarNumeroRegistros() {
        return numeroRegistros;
    }

    /**
     * Extrae y devuelve el último registro almacenado en el historial.
     * 
     * @return El último registro almacenado o null si no hay registros.
     */
    public Registro extraerUltimoRegistro() {
        if (numeroRegistros > 0) {
            Registro ultimoRegistro = almacenamiento[numeroRegistros-1];
            almacenamiento[numeroRegistros-1] = null;
            numeroRegistros--;
            
            return ultimoRegistro;
        } else {
            return null;
        }
    }
}
