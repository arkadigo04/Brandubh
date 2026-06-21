package brandubh.modelo;

import brandubh.util.Sentido;

/**
 * La clase "Jugada" representa un movimiento entre dos celdas en un juego,
 * con atributos de origen y destino. Ofrece métodos para consultar el sentido
 * del movimiento (horizontal, vertical o nulo) y verificar si es un movimiento 
 * horizontal o vertical.
 * @param origen Celda de Origen
 * @param destino Celda de Destino
 * @author Arkaitz Diez y Hector Sanchez
 */
public record Jugada(Celda origen, Celda destino) {
	
	/**
	 * Consulta el sentido de la jugada (horizontal, vertical o nulo).
	 * @return El sentido de la jugada, o null si no tiene sentido (misma celda origen y destino).
	 */
	public Sentido consultarSentido() {
	    int difFilas = destino.consultarCoordenada().fila() - origen.consultarCoordenada().fila();
	    int difColumnas = destino.consultarCoordenada().columna() - origen.consultarCoordenada().columna();

	    if (difFilas == 0 && difColumnas == 0) {
	        return null; // Si las diferencias son ambas cero, la jugada no tiene sentido
	    }

	    // Verificar si es una jugada horizontal o vertical
	    if (difFilas == 0) {
	        // Si la diferencia en las filas es cero, es un movimiento horizontal
	        if (difColumnas > 0) {
	            return Sentido.HORIZONTAL_E;
	        } else {
	            return Sentido.HORIZONTAL_O;
	        }
	    } else if (difColumnas == 0) {
	        // Si la diferencia en las columnas es cero, es un movimiento vertical
	        if (difFilas > 0) {
	            return Sentido.VERTICAL_S;
	        } else {
	            return Sentido.VERTICAL_N;
	        }
	    }

	    return null; // Si no coincide con un movimiento horizontal o vertical, devolver nulo
	}

  
       	
	/**
	 * Verifica si la jugada es un movimiento horizontal o vertical.
	 * @return true si la jugada es horizontal o vertical, false si no lo es o es nula.
	 */
	public boolean esMovimientoHorizontalOVertical() {
	    return consultarSentido() != null; // Devuelve true si la jugada tiene sentido (es horizontal o vertical), false si no lo es o es nula.
	}


}
