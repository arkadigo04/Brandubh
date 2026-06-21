package brandubh.modelo;

import java.util.Arrays;

import brandubh.util.Coordenada;
import brandubh.util.TipoCelda;
import brandubh.util.TipoPieza;

/**
 * Representa el tablero de juego para el juego Brandubh.
 * Contiene métodos para inicializar el tablero y determinar el tipo de celda en una coordenada específica.
 * 
 * @author Arkaitz Díez y Héctor Sánchez
 */
public class Tablero {

	/**
	 * Matriz que representa las celdas del tablero.
	 */
    private Celda[][] celdas; 

    /**
     * Constructor por defecto que inicializa el tablero con sus celdas.
     */
    public Tablero() {
        int filas = 7; // Número de filas
        int columnas = 7; // Número de columnas
        celdas = new Celda[filas][columnas];
        inicializarTablero();
    }

    /**
     * Inicializa el tablero, creando y asignando celdas a cada posición.
     */
    private void inicializarTablero() {
        for (int fila = 0; fila < celdas.length; fila++) {
            for (int columna = 0; columna < celdas[0].length; columna++) {
                Coordenada coordenadaActual = new Coordenada(fila, columna);
                TipoCelda tipo = determinarTipoCelda(coordenadaActual);
                celdas[fila][columna] = new Celda(coordenadaActual, tipo);
            }
        }
    }

    /**
     * Determina el tipo de celda en función de su posición en el tablero.
     * 
     * @param coordenada La coordenada de la celda.
     * @return El tipo de celda (TRONO, PROVINCIA o NORMAL).
     */
    private TipoCelda determinarTipoCelda(Coordenada coordenada) {
        int fila = coordenada.fila();
        int columna = coordenada.columna();
        int centroFila = celdas.length / 2;
        int centroColumna = celdas[0].length / 2;

        if (fila == centroFila && columna == centroColumna) {
            return TipoCelda.TRONO; // Centro del tablero
        } else if ((fila == 0 || fila == celdas.length - 1) && (columna == 0 || columna == celdas[0].length - 1)) {
            return TipoCelda.PROVINCIA; // Esquina del tablero
        } else {
            return TipoCelda.NORMAL; // Resto de las celdas
        }
    }

    /**
     * Convierte el tablero en una representación de texto.
     *
     * @return Una cadena de texto que representa el estado actual del tablero.
     */
	
    public String aTexto() {
        String resultado = "";

        for (int fila = 0; fila <= 6; fila++) {
            resultado += (6-fila + 1) + " ";
            for (int columna = 0; columna < 7; columna++) {
                Coordenada coordenada = new Coordenada(fila, columna);
                Celda celda = consultarCelda(coordenada);
                if (celda != null && !celda.estaVacia()) {
                    TipoPieza tipoPieza = celda.consultarPieza().consultarTipoPieza();
                    if (tipoPieza == TipoPieza.DEFENSOR) {
                        resultado += "D ";
                    } else if (tipoPieza == TipoPieza.ATACANTE) {
                        resultado += "A ";
                    } else if (tipoPieza == TipoPieza.REY) {
                        resultado += "R ";
                    }
                } else {
                    resultado += "- ";
                }
            }
            resultado += "\n";
        }

        resultado += "  a b c d e f g\n";

        return resultado;
    }
    /**
     * Intercambia los caracteres de una cadena de texto.
     * Se utiliza para convertir entre coordenadas de ajedrez (como "a1") y coordenadas de matriz (como "01").
     *
     * @param str La cadena de texto a intercambiar.
     * @return La cadena de texto intercambiada.
     */
    private String swapCharacters(String str) {
        if (str.length() != 2) {
            return null;
        }
        return str.charAt(1) + Character.toString(str.charAt(0)).toUpperCase();
    }

    /**
     * Clona el tablero actual creando un nuevo tablero y copiando todas las celdas.
     *
     * @return Un nuevo objeto Tablero que es una copia del tablero actual.
     */
    public Tablero clonar() {
        Tablero tableroClonado = new Tablero(); // Creamos un nuevo tablero

        // Copiamos cada celda del tablero actual al tablero clonado
        for (int fila = 0; fila < celdas.length; fila++) {
            for (int columna = 0; columna < celdas[0].length; columna++) {
                tableroClonado.celdas[fila][columna] = celdas[fila][columna].clonar();
            }
        }

        return tableroClonado;
    }
    
    /**
     * Coloca una pieza en la coordenada especificada del tablero, si la coordenada es válida
     * y la pieza no es nula. Solo se permite colocar piezas de tipos DEFENSOR, ATACANTE o REY.
     *
     * @param pieza      La pieza a colocar en el tablero.
     * @param coordenada La coordenada en la que se colocará la pieza.
     */
    public void colocar(Pieza pieza, Coordenada coordenada) {
        if (coordenada == null) {
            return; // Sale del método si la coordenada es nula
        }

        if (coordenadaEnTablero(coordenada) && pieza != null) {
            int fila = coordenada.fila();
            int columna = coordenada.columna();
            TipoPieza tipoPieza = pieza.consultarTipoPieza();

            if (tipoPieza == TipoPieza.DEFENSOR || tipoPieza == TipoPieza.ATACANTE || tipoPieza == TipoPieza.REY) {
                celdas[fila][columna].colocar(pieza);
            }
        }
    }

    /**
     * Verifica si una coordenada está dentro de los límites del tablero.
     *
     * @param coordenada La coordenada a verificar.
     * @return true si la coordenada está dentro del tablero, false de lo contrario.
     */
    private boolean coordenadaEnTablero(Coordenada coordenada) {
        int fila = coordenada.fila();
        int columna = coordenada.columna();
        return fila >= 0 && fila < celdas.length && columna >= 0 && columna < celdas[0].length;
    }

    /**
     * Consulta la celda en la coordenada especificada del tablero, devolviendo una copia de la celda.
     *
     * @param coordenada La coordenada de la celda a consultar.
     * @return Una copia de la celda en la coordenada especificada o null si la coordenada no está en el tablero.
     */
    public Celda consultarCelda(Coordenada coordenada) {
        if (coordenadaEnTablero(coordenada)) {
            int fila = coordenada.fila();
            int columna = coordenada.columna();
            return celdas[fila][columna].clonar();
        } else {
            return null;
        }
    }

    /**
     * Consulta todas las celdas del tablero, devolviendo un arreglo de copias de las celdas.
     *
     * @return Un arreglo de copias de todas las celdas del tablero.
     */
    public Celda[] consultarCeldas() {
        int totalCeldas = celdas.length * celdas[0].length;
        Celda[] celdasClonadas = new Celda[totalCeldas];
        int index = 0;

        for (int fila = 0; fila < celdas.length; fila++) {
            for (int columna = 0; columna < celdas[0].length; columna++) {
                celdasClonadas[index] = celdas[fila][columna].clonar();
                index++;
            }
        }

        return celdasClonadas;
    }
    
    /**
     * Consulta las celdas contiguas a la coordenada especificada en el tablero.
     * Se consideran celdas contiguas las adyacentes en las direcciones arriba, abajo, izquierda y derecha.
     *
     * @param coordenada La coordenada de la celda para la cual se buscan celdas contiguas.
     * @return Un arreglo de celdas contiguas a la coordenada especificada.
     */
    public Celda[] consultarCeldasContiguas(Coordenada coordenada) {
    	 int fila = coordenada.fila();
    	    int columna = coordenada.columna();
    	    int contador = 0;
    	    int maxFila = 6;
    	    int maxColumna = 6;
    	    
    	    // Verificar límites de la cuadrícula
    	    if (fila < 0 || fila > maxFila || columna < 0 || columna > maxColumna) {
    	        return new Celda[0]; // Devolver un array vacío si está fuera de la cuadrícula
    	    }
    	    
    	    // Calcular la cantidad de celdas contiguas posibles
    	    if (fila - 1 >= 0) { 
    	    	contador++; 
    	    }
    	    if (fila + 1 <= maxFila) { 
    	    	contador++; 
    	    }
    	    if (columna - 1 >= 0) { 
    	    	contador++;
    	    }
    	    if (columna + 1 <= maxColumna) { 
    	    	contador++;
    	    }
    	    
    	    // Crear el array de celdas contiguas
    	    Celda[] coordenadasContiguas = new Celda[contador];
    	    contador = 0; // Reiniciar el contador
    	    
    	    // Almacenar celdas contiguas si están dentro de la cuadrícula
    	    if (fila - 1 >= 0) {
    	        coordenadasContiguas[contador] = celdas[fila - 1][columna].clonar();
    	        contador++;
    	    }
    	    if (fila + 1 <= maxFila) {
    	        coordenadasContiguas[contador] = celdas[fila + 1][columna].clonar();
    	        contador++;
    	    }
    	    if (columna - 1 >= 0) {
    	        coordenadasContiguas[contador] = celdas[fila][columna - 1].clonar();
    	        contador++;
    	    }
    	    if (columna + 1 <= maxColumna) {
    	        coordenadasContiguas[contador] = celdas[fila][columna + 1].clonar();
    	        contador++;
    	    }
    	    
    	    return coordenadasContiguas;
       
    }


    /**
     * Verifica si una coordenada representa una celda válida en el tablero.
     *
     * @param fila    La fila de la coordenada.
     * @param columna La columna de la coordenada.
     * @return true si la coordenada está dentro del tablero, false de lo contrario.
     */
    private boolean esCeldaValida(int fila, int columna) {
        return fila >= 0 && fila < celdas.length && columna >= 0 && columna < celdas[0].length;
    }

    /**
     * Consulta las celdas contiguas en sentido horizontal a la coordenada especificada en el tablero.
     * Se consideran celdas contiguas las adyacentes en las direcciones izquierda y derecha.
     *
     * @param coordenada La coordenada de la celda para la cual se buscan celdas contiguas horizontales.
     * @return Un arreglo de celdas contiguas horizontales a la coordenada especificada.
     */
    public Celda[] consultarCeldasContiguasEnHorizontal(Coordenada coordenada) {
    	int fila = coordenada.fila();
 	    int columna = coordenada.columna();
 	    int contador = 0;
 	    int maxFila = 6;
 	    int maxColumna = 6;
 	    
 	    // Verificar límites de la cuadrícula
 	    if (fila < 0 || fila > maxFila || columna < 0 || columna > maxColumna) {
 	        return new Celda[0]; // Devolver un array vacío si está fuera de la cuadrícula
 	    }
 	    
 	    // Calcular la cantidad de celdas contiguas posibles
 	    if (columna - 1 >= 0) { 
 	    	contador++;
 	    }
 	    if (columna + 1 <= maxColumna) { 
 	    	contador++;
 	    }
 	    
 	    // Crear el array de celdas contiguas
 	    Celda[] coordenadasContiguas = new Celda[contador];
 	    contador = 0; // Reiniciar el contador
 	    
 	    // Almacenar celdas contiguas si están dentro de la cuadrícula
 	    if (columna - 1 >= 0) {
 	        coordenadasContiguas[contador] = celdas[fila][columna - 1].clonar();
 	        contador++;
 	    }
 	    if (columna + 1 <= maxColumna) {
 	        coordenadasContiguas[contador] = celdas[fila][columna + 1].clonar();
 	        contador++;
 	    }
 	    
 	    return coordenadasContiguas;
    }

   /**
	 * Consulta y devuelve un array de celdas contiguas en sentido vertical con respecto a la coordenada proporcionada.
	 * 
	 * @param coordenada La coordenada de referencia para obtener las celdas contiguas.
	 * @return Un array de celdas contiguas en sentido vertical o un array vacío si está fuera de la cuadrícula.
	 */
	public Celda[] consultarCeldasContiguasEnVertical(Coordenada coordenada) {
	    int fila = coordenada.fila();
	    int columna = coordenada.columna();
	    int contador = 0;
	    int maxFila = 6;
	    int maxColumna = 6;
	    
	    // Verificar límites de la cuadrícula
	    if (fila < 0 || fila > maxFila || columna < 0 || columna > maxColumna) {
	        return new Celda[0]; // Devolver un array vacío si está fuera de la cuadrícula
	    }
	    
	    // Calcular la cantidad de celdas contiguas posibles
	    if (fila - 1 >= 0) { 
	        contador++; 
	    }
	    if (fila + 1 <= maxFila) { 
	        contador++; 
	    }
	    
	    // Crear el array de celdas contiguas
	    Celda[] coordenadasContiguas = new Celda[contador];
	    contador = 0; // Reiniciar el contador
	    
	    // Almacenar celdas contiguas si están dentro de la cuadrícula
	    if (fila - 1 >= 0) {
	        coordenadasContiguas[contador] = celdas[fila - 1][columna].clonar();
	        contador++;
	    }
	    if (fila + 1 <= maxFila) {
	        coordenadasContiguas[contador] = celdas[fila + 1][columna].clonar();
	        contador++;
	    }
	    
	    return coordenadasContiguas;
	}



    /**
     * Consulta el número de columnas en el tablero.
     *
     * @return El número de columnas en el tablero.
     */
    public int consultarNumeroColumnas() {
        return celdas[0].length;
    }

    /**
     * Consulta el número de filas en el tablero.
     *
     * @return El número de filas en el tablero.
     */
    public int consultarNumeroFilas() {
        return celdas.length;
    }

    /**
     * Consulta el número de piezas de un tipo específico en el tablero.
     *
     * @param tipoPieza El tipo de pieza a contar.
     * @return El número de piezas del tipo especificado en el tablero.
     */
    public int consultarNumeroPiezas(TipoPieza tipoPieza) {
        int contadorPiezas = 0;

        for (Celda[] fila : celdas) {
            for (Celda celda : fila) {
                if (!celda.estaVacia() && celda.consultarPieza().consultarTipoPieza() == tipoPieza) {
                    contadorPiezas++;
                }
            }
        }

        return contadorPiezas;
    }

    /**
     * Elimina la pieza de la celda en la coordenada especificada.
     *
     * @param coordenada La coordenada de la celda que contiene la pieza a eliminar.
     */
    public void eliminarPieza(Coordenada coordenada) {
        if (coordenada != null && coordenadaEnTablero(coordenada)) {
            int fila = coordenada.fila();
            int columna = coordenada.columna();
            celdas[fila][columna].eliminarPieza();
        }
    }

    /**
     * Obtiene la celda en la coordenada especificada del tablero.
     *
     * @param coordenada La coordenada de la celda a obtener.
     * @return La celda en la coordenada especificada o null si la coordenada está fuera del tablero.
     */
    public Celda obtenerCelda(Coordenada coordenada) {
        if (coordenadaEnTablero(coordenada)) {
            int fila = coordenada.fila();
            int columna = coordenada.columna();
            return celdas[fila][columna];
        } else {
            return null; // Devuelve null si la coordenada está fuera del tablero
        }
    }

    // Métodos equals, hashCode y toString

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.deepHashCode(celdas);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tablero other = (Tablero) obj;
        return Arrays.deepEquals(celdas, other.celdas);
    }

    @Override
    public String toString() {
        return "Tablero [celdas=" + Arrays.toString(celdas) + "]";
    }
}