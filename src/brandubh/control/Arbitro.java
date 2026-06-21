package brandubh.control;

import brandubh.modelo.Celda;
import brandubh.modelo.Jugada;
import brandubh.modelo.Tablero;
import brandubh.util.Color;
import brandubh.util.TipoPieza;
import brandubh.modelo.Pieza;
import brandubh.util.Coordenada;
import brandubh.util.Sentido;
import brandubh.util.TipoCelda;

/**
 * Arbitro del juego, gestionando movimientos, turnos, 
 * colocación de piezas, verificaciones de victoria y capturas en un tablero de
 * juego determinado.
 * 
 * @author Arkaitz Diez y Hector Sanchez
 */
public class Arbitro {
	
		/**
		 * Representa el tablero de juego en el cual se llevan a cabo las acciones y movimientos.
		 */
		private Tablero tablero;
	
		/**
		 * Almacena el número de la jugada actual en el juego.
		 */
		private int numeroJugada;
	
		/**
		 * Indica el turno actual del jugador, representado por el color de las piezas.
		 */
		private Color turno;
	
		/**
		 * Contiene la información del último movimiento realizado en el juego.
		 */
		private Jugada ultimoMovimiento;
	
		/**
		 * Contiene el historial para cada jugada
		 */
		private Historial historial;

		/**
		 * Crea un árbitro para supervisar un juego.
		 * @param tablero El tablero sobre el cual se llevará a cabo el juego.
		 */
		public Arbitro(Tablero tablero) {
		    this.tablero = tablero; // Establece el tablero proporcionado para el juego.
		    
		    numeroJugada = 0; // Inicializa el número de jugada en cero.
		    // Inicializar el turno con los atacantes (color negro)
		    historial = new Historial(); // Crea un nuevo objeto Historial para mantener un registro de las jugadas.
		}
	
		/**
		 * Cambia el turno de juego al siguiente jugador y aumenta el número de jugada.
		 */
		public void cambiarTurno() {
		    turno = turno == Color.BLANCO ? Color.NEGRO : Color.BLANCO;
		}

		/**
		 * Coloca piezas en el tablero según los tipos y coordenadas proporcionados para un determinado color.
		 * @param tipo       Array de tipos de pieza a colocar.
		 * @param coordenadas Array de coordenadas donde colocar las piezas.
		 * @param turno      El color de las piezas a colocar.
		 */
		public void colocarPiezas(TipoPieza[] tipo, int[][] coordenadas, Color turno) {
		    for (int i = 0; i < tipo.length; i++) {
		        int fila = coordenadas[i][0];
		        int columna = coordenadas[i][1]; // Se corrigió el índice para obtener la columna correctamente.
		        Coordenada coordenada = new Coordenada(fila, columna);
		        tablero.colocar(new Pieza(tipo[i]), coordenada); // Coloca una pieza en la coordenada dada.
		        this.turno = turno;
		        Registro registroJugada = new Registro (tablero.clonar(), ultimoMovimiento);
		        historial.añadirUltimoRegistro(registroJugada);
			    
		        
		    }
		}
	
	
	

		/**
		 * Coloca las piezas en el tablero para la configuración inicial del juego.
		 * Este método coloca las piezas de los distintos tipos (Rey, Defensores y Atacantes)
		 * en posiciones específicas en el tablero, estableciendo el turno inicial como el color negro.
		 */
		public void colocarPiezasConfiguracionInicial() {
		    tablero.colocar(new Pieza(TipoPieza.REY), new Coordenada(3, 3));

		    // Defensores
		    tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(3, 2));
		    tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(2, 3));
		    tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(3, 4));
		    tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(4, 3));

		    // Atacantes
		    tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 0));
		    tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 1));
		    tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(0, 3));
		    tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(1, 3));
		    tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 6));
		    tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(3, 5));
		    tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(5, 3));
		    tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(6, 3));
		    turno = Color.NEGRO; // Establece el turno inicial como el color negro.
		}
	 
		/**
		 * Consulta el número de jugada actual.
		 * @return El número de jugada actual.
		 */
		public int consultarNumeroJugada() {
		    return numeroJugada; // Devuelve el número de jugada actual.
		}

		
		/**
		 * Consulta el tablero actual.
		 *
		 * @return Una copia del tablero actual.
		 */
		public Tablero consultarTablero() {
		    return tablero.clonar(); // Devuelve una copia del tablero actual.
		}

		/**
		 * Consulta el color del jugador al que le corresponde jugar.
		 * @return El color del jugador al que le corresponde jugar en este momento.
		 */
		public Color consultarTurno() {
		    return turno; // Devuelve el color del jugador al que le corresponde jugar.
		}

		/**
		 * Método privado para verificar si una coordenada está dentro de los límites del tablero.
		 * @param coordenada La coordenada a verificar.
		 * @return true si la coordenada está dentro del tablero, false si está fuera de los límites.
		 */
		private boolean estaEnTablero(Coordenada coordenada) {
		    int fila = coordenada.fila();
		    int columna = coordenada.columna();
		    return fila >= 0 && fila < 7 && columna >= 0 && columna < 7;
		}

		/**
		 * Verifica si un movimiento realizado en el juego es legal o no.
		 * Se evalúan varios criterios, incluyendo la existencia de la jugada,
		 * la validez de las celdas de origen y destino, la legalidad del movimiento,
		 * y reglas específicas del juego como el trono y la provincia.
		 * 
		 * @param jugada La jugada a evaluar.
		 * @return true si el movimiento es legal, false de lo contrario.
		 */
		public boolean esMovimientoLegal(Jugada jugada) {
		    // Verifica si la jugada es nula
		    if (jugada == null) {
		        return false;
		    }
		
		    // Obtiene las celdas de origen y destino
		    Celda origen = obtenerCeldaPorCoordenada(jugada.origen().consultarCoordenada());
		    Celda destino = obtenerCeldaPorCoordenada(jugada.destino().consultarCoordenada());
		
		    // Verifica si las celdas de origen y destino son nulas
		    if (origen == null || destino == null) {
		        return false;
		    }
		
		    // Verifica si la celda de origen está vacía o si el movimiento no cumple con las reglas del trono
		    if (origen.estaVacia() || !esTronoLegal(destino, origen)) {
		        return false;
		    }
		
		    // Verifica si las coordenadas de origen y destino están dentro de los límites del tablero
		    // y si la celda de destino está vacía
		    if (!estaDentroLimitesTablero(origen.consultarCoordenada(), destino.consultarCoordenada()) ||
		            !destino.estaVacia()) {
		        return false;
		    }
		
		    // Verifica si la celda de origen contiene una pieza
		    Coordenada coordenadaOrigen = origen.consultarCoordenada();
		    if (!celdaOrigenConPieza(coordenadaOrigen)) {
		        return false;
		    }
		
		    // Verifica si el movimiento es legal según las reglas específicas del juego
		    Coordenada coordenadaDestino = destino.consultarCoordenada();
		    if (!verificarMovimientoLegal(origen, destino, coordenadaOrigen, coordenadaDestino)) {
		        return false;
		    }
		
		    // Verifica si el destino es una celda de tipo PROVINCIA y si la pieza de origen es un rey
		    if (destino.consultarTipoCelda() == TipoCelda.PROVINCIA && !esPiezaRey(origen)) {
		        return false;
		    }
		
		    // Si todas las condiciones anteriores se cumplen, el movimiento se considera legal
		    return true;
		}

		/**
		 * Verifica si el movimiento entre dos coordenadas está dentro de los límites del tablero.
		 * 
		 * @param origen   Coordenada de origen.
		 * @param destino  Coordenada de destino.
		 * @return true si el movimiento está dentro de los límites del tablero, false de lo contrario.
		 */
		private boolean estaDentroLimitesTablero(Coordenada origen, Coordenada destino) {
		    int difFilas = destino.fila() - origen.fila();
		    int difColumnas = destino.columna() - origen.columna();
		    return !(difFilas < -6 || difFilas > 6 || difColumnas < -6 || difColumnas > 6);
		}

		/**
		 * Verifica si la pieza en la celda dada es un rey.
		 * 
		 * @param celda La celda a verificar.
		 * @return true si la pieza en la celda es un rey, false de lo contrario.
		 */
		private boolean esPiezaRey(Celda celda) {
		    return celda.consultarPieza().consultarTipoPieza() == TipoPieza.REY;
		}

		/**
		 * Verifica si un movimiento entre dos celdas es legal según las reglas del juego.
		 * 
		 * @param origen            Celda de origen.
		 * @param destino           Celda de destino.
		 * @param coordenadaOrigen  Coordenada de origen.
		 * @param coordenadaDestino Coordenada de destino.
		 * @return true si el movimiento es legal, false de lo contrario.
		 */
		private boolean verificarMovimientoLegal(Celda origen, Celda destino, Coordenada coordenadaOrigen, Coordenada coordenadaDestino) {
		    if (!existenPiezasEntreCeldas(origen, destino, coordenadaOrigen, coordenadaDestino)) {
		        return false;
		    }

		    int difFilas = coordenadaDestino.fila() - coordenadaOrigen.fila();
		    int difColumnas = coordenadaDestino.columna() - coordenadaOrigen.columna();

		    return !(difFilas != 0 && difColumnas != 0) &&
		            (coordenadaOrigen.fila() == coordenadaDestino.fila() || coordenadaOrigen.columna() == coordenadaDestino.columna()) &&
		            !coordenadaOrigen.equals(coordenadaDestino);
		}

		/**
		 * Obtiene una celda del tablero según la coordenada dada.
		 * 
		 * @param coordenada La coordenada de la celda.
		 * @return La celda correspondiente a la coordenada.
		 */
		private Celda obtenerCeldaPorCoordenada(Coordenada coordenada) {
		    return tablero.consultarCelda(coordenada);
		}

		/**
		 * Verifica si existen piezas entre dos celdas en una jugada específica.
		 * 
		 * @param origen            Celda de origen.
		 * @param destino           Celda de destino.
		 * @param coordenadaOrigen  Coordenada de origen.
		 * @param coordenadaDestino Coordenada de destino.
		 * @return true si no hay piezas entre las celdas, false si hay alguna pieza intermedia.
		 */
		private boolean existenPiezasEntreCeldas(Celda origen, Celda destino, Coordenada coordenadaOrigen, Coordenada coordenadaDestino) {
		    Jugada jugada = new Jugada(origen, destino);
		    int numCeldasVerticales = Math.abs(coordenadaOrigen.fila() - coordenadaDestino.fila());
		    int numCeldasHorizontales = Math.abs(coordenadaOrigen.columna() - coordenadaDestino.columna());

		    for (int i = 1; i < Math.max(numCeldasVerticales, numCeldasHorizontales); i++) {
		        // Cálculo de la fila y columna intermedia según el sentido de la jugada
		        int filaIntermedia = coordenadaOrigen.fila();
		        int columnaIntermedia = coordenadaOrigen.columna();

		        if (jugada.consultarSentido() == Sentido.VERTICAL_N) {
		            filaIntermedia -= i;
		        } else if (jugada.consultarSentido() == Sentido.VERTICAL_S) {
		            filaIntermedia += i;
		        } else if (jugada.consultarSentido() == Sentido.HORIZONTAL_E) {
		            columnaIntermedia += i;
		        } else if (jugada.consultarSentido() == Sentido.HORIZONTAL_O) {
		            columnaIntermedia -= i;
		        }

		        Coordenada coordIntermedia = new Coordenada(filaIntermedia, columnaIntermedia);
		        Celda celdaIntermedia = tablero.consultarCelda(coordIntermedia);

		        // Verifica si la celda intermedia contiene una pieza de atacante o defensor
		        if (celdaIntermedia.consultarPieza() != null &&
		            (celdaIntermedia.consultarPieza().consultarTipoPieza() == TipoPieza.ATACANTE ||
		             celdaIntermedia.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR)) {
		            return false; // Existe una pieza intermedia, el movimiento no es legal
		        }
		    }
		    return true; // No hay piezas intermedias, el movimiento es legal
		}


		/**
		 * Verifica si el trono es legal para la ocupación según las reglas del juego.
		 * El trono no puede ser ocupado por un defensor.
		 * 
		 * @param destino Celda de destino que representa el trono.
		 * @param origen  Celda de origen desde donde se realiza el movimiento.
		 * @return true si el trono es legal para la ocupación, false de lo contrario.
		 */
		private boolean esTronoLegal(Celda destino, Celda origen) {
		    if (destino.consultarTipoCelda().equals(TipoCelda.TRONO)) {
		        if (origen.consultarPieza() != null) {
		            if (origen.consultarPieza().consultarTipoPieza() == TipoPieza.DEFENSOR) {
		                return false;
		            }
		        }
		    }
		    return true;
		}

		/**
		 * Verifica si la celda de origen contiene una pieza del turno actual.
		 * 
		 * @param coordenada La coordenada de la celda de origen.
		 * @return true si la celda de origen contiene una pieza del turno actual, false de lo contrario.
		 */
		private boolean celdaOrigenConPieza(Coordenada coordenada) {
		    Pieza piezaOrigen = tablero.obtenerCelda(coordenada).consultarPieza();
		    if (piezaOrigen == null || piezaOrigen.consultarColor() != turno) {
		        return false;
		    }
		    return true;
		}

		/**
		 * Verifica si el bando de los Atacantes ha ganado la partida.
		 * Se considera que los Atacantes ganan si capturan al menos una pieza defensora.
		 * 
		 * @return true si los Atacantes han ganado, false en caso contrario.
		 */
		public boolean haGanadoAtacante() {
		    Celda celdaResultante = ultimoMovimiento.destino();
		    Pieza piezaResultante = ultimoMovimiento.origen().consultarPieza();

		    if (piezaResultante == null) {
		        piezaResultante = ultimoMovimiento.destino().consultarPieza();
		    }

		    Celda[] contiguas = tablero.consultarCeldasContiguas(celdaResultante.consultarCoordenada());
		    int longitudContiguas = contiguas.length;

		    for (int i = 0; i < longitudContiguas; i++) {
		        if (contiguas[i].consultarPieza() != null &&
		            contiguas[i].consultarPieza().consultarTipoPieza() != piezaResultante.consultarTipoPieza()) {

		            Celda celdaPosibleCaptura = contiguas[i];
		            Celda[] contiguasComestiblesH = tablero.consultarCeldasContiguasEnHorizontal(celdaPosibleCaptura.consultarCoordenada());
		            Celda[] contiguasComestiblesV = tablero.consultarCeldasContiguasEnVertical(celdaPosibleCaptura.consultarCoordenada());

		            int longitudComestiblesH = contiguasComestiblesH.length;
		            int longitudComestiblesV = contiguasComestiblesV.length;

		            if (ganarNormal(longitudComestiblesV, longitudComestiblesH, contiguasComestiblesH, contiguasComestiblesV, celdaPosibleCaptura, piezaResultante)) {
		                return true;
		            }

		            if (ganarUsandoProvincias(longitudComestiblesV, longitudComestiblesH, contiguasComestiblesH, contiguasComestiblesV, celdaPosibleCaptura, piezaResultante)) {
		                return true;
		            }
		        }
		    }
		    return false;
		}

		/**
		 * Verifica si se puede ganar la partida normalmente al cumplir condiciones específicas.
		 * En este caso, se considera ganado si se elimina la cantidad de piezas indicada.
		 * 
		 * @param nV                    Número de celdas verticales contiguas.
		 * @param nH                    Número de celdas horizontales contiguas.
		 * @param contiguasPosibleCapturaH Celdas contiguas en horizontal para posible captura.
		 * @param contiguasPosibleCapturaV Celdas contiguas en vertical para posible captura.
		 * @param celdaPosibleCaptura   Celda donde se podría realizar la captura.
		 * @param piezaMovida           Pieza que realiza el movimiento.
		 * @return true si se cumple la condición para ganar normalmente, false de lo contrario.
		 */
		private boolean ganarNormal(int nV, int nH, Celda[] contiguasPosibleCapturaH, Celda[] contiguasPosibleCapturaV, Celda celdaPosibleCaptura, Pieza piezaMovida) {
		    if (cumpleCondiciones(nV, contiguasPosibleCapturaV, piezaMovida, 2)) {
		        return celdaPosibleCaptura.consultarPieza().consultarTipoPieza() == TipoPieza.REY;
		    }

		    if (cumpleCondiciones(nH, contiguasPosibleCapturaH, piezaMovida, 2)) {
		        return celdaPosibleCaptura.consultarPieza().consultarTipoPieza() == TipoPieza.REY;
		    }

		    return false;
		}

		/**
		 * Verifica si se cumplen las condiciones para ganar, es decir, se eliminan la cantidad de piezas indicada.
		 * 
		 * @param n              Número de celdas contiguas.
		 * @param contiguasPosibleCaptura Celdas contiguas para posible captura.
		 * @param piezaMovida    Pieza que realiza el movimiento.
		 * @param condicion      Cantidad de piezas a eliminar para cumplir la condición.
		 * @return true si se cumple la condición, false de lo contrario.
		 */
		private boolean cumpleCondiciones(int n, Celda[] contiguasPosibleCaptura, Pieza piezaMovida, int condicion) {
		    int eliminados = 0;

		    for (int i = 0; i < n; i++) {
		        if (contiguasPosibleCaptura[i].consultarPieza() != null &&
		            contiguasPosibleCaptura[i].consultarPieza().consultarTipoPieza() == piezaMovida.consultarTipoPieza()) {
		            eliminados++;
		        }
		    }

		    return eliminados == condicion;
		}

		/**
		 * Verifica si se puede ganar la partida utilizando provincias al cumplir condiciones específicas.
		 * En este caso, se considera ganado si se elimina una pieza del oponente al llegar a una provincia.
		 * 
		 * @param nV                      Número de celdas verticales contiguas.
		 * @param nH                      Número de celdas horizontales contiguas.
		 * @param contiguasPosibleCapturaH Celdas contiguas en horizontal para posible captura.
		 * @param contiguasPosibleCapturaV Celdas contiguas en vertical para posible captura.
		 * @param celdaPosibleCaptura     Celda donde se podría realizar la captura.
		 * @param pieza                   Pieza que realiza el movimiento.
		 * @return true si se cumple la condición para ganar utilizando provincias, false de lo contrario.
		 */
		private boolean ganarUsandoProvincias(int nV, int nH, Celda[] contiguasPosibleCapturaH, Celda[] contiguasPosibleCapturaV, Celda celdaPosibleCaptura, Pieza pieza) {
		    boolean comerPiezaH = false;
		    boolean comerPiezaV = false;
		    int celdaConProvinciaHPosicion = -1;
		    int celdaConProvinciaVPosicion = -1;

		    for (int i = 0; i < nH; i++) {
		        if (contiguasPosibleCapturaH[i].consultarTipoCelda() == TipoCelda.PROVINCIA) {
		            comerPiezaH = true;
		            celdaConProvinciaHPosicion = i;
		            break; // Salir del bucle una vez se encuentra una celda de provincia
		        }
		    }

		    for (int j = 0; j < nV; j++) {
		        if (contiguasPosibleCapturaV[j].consultarTipoCelda() == TipoCelda.PROVINCIA) {
		            comerPiezaV = true;
		            celdaConProvinciaVPosicion = j;
		            break; // Salir del bucle una vez se encuentra una celda de provincia
		        }
		    }

		    if (comerPiezaH && celdaConProvinciaHPosicion != -1) {
		        return validarMovimientoProvincia(contiguasPosibleCapturaH, celdaConProvinciaHPosicion, celdaPosibleCaptura, pieza);
		    }

		    if (comerPiezaV && celdaConProvinciaVPosicion != -1) {
		        return validarMovimientoProvincia(contiguasPosibleCapturaV, celdaConProvinciaVPosicion, celdaPosibleCaptura, pieza);
		    }

		    return false;
		}

		/**
		 * Valida el movimiento realizado en una provincia para determinar si se cumple la condición de ganar.
		 * 
		 * @param contiguasPosibleCaptura Celdas contiguas para posible captura.
		 * @param celdaConProvinciaPosicion Posición de la celda con provincia en el arreglo de celdas contiguas.
		 * @param celdaPosibleCaptura     Celda donde se podría realizar la captura.
		 * @param pieza                   Pieza que realiza el movimiento.
		 * @return true si se cumple la condición, false de lo contrario.
		 */
		private boolean validarMovimientoProvincia(Celda[] contiguasPosibleCaptura, int celdaConProvinciaPosicion, Celda celdaPosibleCaptura, Pieza pieza) {
		    if (celdaConProvinciaPosicion == 0 && contiguasPosibleCaptura[1].consultarPieza() != null &&
		        contiguasPosibleCaptura[1].consultarPieza().consultarTipoPieza() == pieza.consultarTipoPieza()) {
		        return celdaPosibleCaptura.consultarPieza().consultarTipoPieza() == TipoPieza.REY;
		    }

		    if (celdaConProvinciaPosicion == 1 && contiguasPosibleCaptura[0].consultarPieza() != null &&
		        contiguasPosibleCaptura[0].consultarPieza().consultarTipoPieza() == pieza.consultarTipoPieza()) {
		        return celdaPosibleCaptura.consultarPieza().consultarTipoPieza() == TipoPieza.REY;
		    }

		    return false;
		}


		/**
		 * Verifica si el bando del Rey ha ganado la partida al tener al Rey en una provincia.
		 * 
		 * @return true si el Rey está en una provincia, false en caso contrario.
		 */
		public boolean haGanadoRey() {
		    for (int i = 0; i < 7; i++) {
		        for (int j = 0; j < 7; j++) {
		            Coordenada coordenada = new Coordenada(i, j);
		            Celda celda = tablero.obtenerCelda(coordenada);

		            if (celda.consultarTipoCelda() == TipoCelda.PROVINCIA && 
		                celda.consultarPieza() != null && 
		                celda.consultarPieza().consultarTipoPieza() == TipoPieza.REY) {
		                return true;
		            }
		        }
		    }
		    return false;
		}

		/**
		 * Realiza el movimiento en el tablero según la jugada especificada.
		 * 
		 * @param jugada La jugada que indica el movimiento a realizar.
		 */
		public void mover(Jugada jugada) {
		    Tablero tableroActual = tablero.clonar();
		    Registro registroJugada = new Registro(tableroActual, ultimoMovimiento);
		    historial.añadirUltimoRegistro(registroJugada);

		    // Obtener las coordenadas de origen y destino desde la jugada
		    Coordenada coordenadaOrigen = jugada.origen().consultarCoordenada();
		    Coordenada coordenadaDestino = jugada.destino().consultarCoordenada();

		    // Obtener las celdas de origen y destino desde el tablero usando las coordenadas obtenidas
		    Celda celdaOrigen = tablero.obtenerCelda(coordenadaOrigen);
		    Celda celdaDestino = tablero.obtenerCelda(coordenadaDestino);

		    // Verificar si las celdas de origen y destino son válidas
		    if (celdaOrigen == null || celdaDestino == null) {
		        // Manejar el caso donde las celdas no son válidas
		        return;
		    }

		    // Obtener la pieza de la celda de origen
		    Pieza piezaOrigen = celdaOrigen.consultarPieza();

		    // Verificar si la celda de origen contiene una pieza válida
		    if (piezaOrigen == null) {
		        // Manejar el caso donde la celda de origen está vacía o no contiene una pieza válida
		        return;
		    }

		    // Clonar la pieza de la celda de origen para colocarla en la celda de destino
		    Pieza piezaJugada = new Pieza(piezaOrigen.consultarTipoPieza());

		    // Mover la pieza de la celda de origen a la celda de destino
		    tablero.eliminarPieza(coordenadaOrigen);
		    tablero.colocar(piezaJugada, coordenadaDestino);

		    // Actualizar el registro del último movimiento y el número de jugada
		    this.ultimoMovimiento = jugada;
		    numeroJugada++;
		}

		/**
		 * Realiza capturas de piezas en el tablero después de un movimiento.
		 * Verifica si hay piezas en el camino del último movimiento y, en caso de ser del oponente,
		 * las elimina del tablero.
		 */
		public void realizarCapturasTrasMover() {
		    Celda celdaUltimoMovimiento = ultimoMovimiento.destino();
		    Pieza pieza = ultimoMovimiento.origen().consultarPieza();

		    if (pieza == null) {
		        pieza = ultimoMovimiento.destino().consultarPieza();
		    }

		    Celda[] celdasContiguas = tablero.consultarCeldasContiguas(celdaUltimoMovimiento.consultarCoordenada());
		    int numeroContiguas = celdasContiguas.length;

		    for (int i = 0; i < numeroContiguas; i++) {
		        Celda captura = celdasContiguas[i];

		        Celda[] celdasContiguasHorizontal = tablero.consultarCeldasContiguasEnHorizontal(captura.consultarCoordenada());
		        Celda[] celdasContiguasVertical = tablero.consultarCeldasContiguasEnVertical(captura.consultarCoordenada());

		        int numeroContiguasHorizontal = celdasContiguasHorizontal.length;
		        int numeroContiguasVertical = celdasContiguasVertical.length;

		        capturaNormal(celdaUltimoMovimiento, captura, pieza, numeroContiguas, celdasContiguasHorizontal, celdasContiguasVertical, numeroContiguasHorizontal, numeroContiguasVertical);
		        capturaEnProvincias(celdaUltimoMovimiento, pieza, numeroContiguas, captura, celdasContiguasHorizontal, celdasContiguasVertical, numeroContiguasHorizontal, numeroContiguasVertical);
		    }
		}

		/**
		 * Realiza la captura normal de piezas después de un movimiento en las direcciones vertical y horizontal.
		 * 
		 * @param celdaUltimoMovimiento Celda de destino del último movimiento.
		 * @param captura Celda contigua a la celda de destino.
		 * @param pieza Pieza que se movió en el último movimiento.
		 * @param numeroContiguas Número de celdas contiguas a la celda de destino.
		 * @param celdasContiguasHorizontal Celdas contiguas en dirección horizontal.
		 * @param celdasContiguasVertical Celdas contiguas en dirección vertical.
		 * @param numeroContiguasHorizontal Número de celdas contiguas en dirección horizontal.
		 * @param numeroContiguasVertical Número de celdas contiguas en dirección vertical.
		 */
		private void capturaNormal(Celda celdaUltimoMovimiento, Celda captura, Pieza pieza, int numeroContiguas, Celda[] celdasContiguasHorizontal, Celda[] celdasContiguasVertical, int numeroContiguasHorizontal, int numeroContiguasVertical) {
		    if (numeroContiguasVertical == 2) {
		        int piezasPegadasVertical = contarPiezasPegadas(celdasContiguasVertical, pieza, numeroContiguasVertical);
		        realizarCapturaSiCorresponde(captura, piezasPegadasVertical);
		    }

		    if (numeroContiguasHorizontal == 2) {
		        int piezasPegadasHorizontal = contarPiezasPegadas(celdasContiguasHorizontal, pieza, numeroContiguasHorizontal);
		        realizarCapturaSiCorresponde(captura, piezasPegadasHorizontal);
		    }
		}

		/**
		 * Cuenta el número de piezas pegadas a una celda en un conjunto de celdas contiguas.
		 * 
		 * @param celdasContiguas Conjunto de celdas contiguas.
		 * @param pieza Pieza que se busca contar.
		 * @param longitud Longitud del conjunto de celdas contiguas.
		 * @return Número de piezas pegadas a la celda.
		 */
		private int contarPiezasPegadas(Celda[] celdasContiguas, Pieza pieza, int longitud) {
		    int piezasPegadas = 0;
		    if (pieza != null) {

		        for (int i = 0; i < longitud; i++) {
		            if (celdasContiguas[i].consultarPieza() != null && celdasContiguas[i].consultarPieza().consultarTipoPieza() == pieza.consultarTipoPieza()) {
		                piezasPegadas++;
		            }
		        }

		    }
		    return piezasPegadas;
		}

		/**
		 * Realiza la captura de una celda contigua si se cumplen las condiciones.
		 * 
		 * @param captura Celda contigua a la celda de destino.
		 * @param piezasPegadas Número de piezas pegadas a la celda contigua.
		 */
		private void realizarCapturaSiCorresponde(Celda captura, int piezasPegadas) {
		    if (piezasPegadas == 2 && captura.consultarPieza() != null && captura.consultarPieza().consultarTipoPieza() != TipoPieza.REY) {
		        tablero.eliminarPieza(captura.consultarCoordenada());
		    }
		}

		/**
		 * Realiza capturas de piezas en el tablero después de un movimiento en el área de provincias.
		 * 
		 * @param celdaUltimoMovimiento Celda de destino del último movimiento.
		 * @param pieza Pieza que se movió en el último movimiento.
		 * @param numeroContiguas Número de celdas contiguas a la celda de destino.
		 * @param captura Celda contigua a la celda de destino.
		 * @param celdasContiguasHorizontal Celdas contiguas en dirección horizontal.
		 * @param celdasContiguasVertical Celdas contiguas en dirección vertical.
		 * @param numeroContiguasHorizontal Número de celdas contiguas en dirección horizontal.
		 * @param numeroContiguasVertical Número de celdas contiguas en dirección vertical.
		 */
		private void capturaEnProvincias(Celda celdaUltimoMovimiento, Pieza pieza, int numeroContiguas, Celda captura, Celda[] celdasContiguasHorizontal, Celda[] celdasContiguasVertical, int numeroContiguasHorizontal, int numeroContiguasVertical) {
		    if (pieza != null) {
		        boolean capturarVertical = false;
		        boolean capturarHorizontal = false;

		        int celdasTipoProvinciaHorizontales = 0;
		        int celdasTipoProvinciaVerticales = 0;

		        for (int i = 0; i < numeroContiguasVertical; i++) {
		            if (celdasContiguasVertical[i].consultarTipoCelda() == TipoCelda.PROVINCIA) {
		                capturarVertical = true;
		                celdasTipoProvinciaVerticales = i;
		            }
		        }

		        if (capturarVertical && celdasTipoProvinciaVerticales == 1) {
		            realizarCapturaEnProvincia(celdasContiguasVertical[0], captura, pieza);
		        }

		        if (capturarVertical && celdasTipoProvinciaVerticales == 0) {
		            realizarCapturaEnProvincia(celdasContiguasVertical[1], captura, pieza);
		        }

		        for (int j = 0; j < numeroContiguasHorizontal; j++) {
		            if (celdasContiguasHorizontal[j].consultarTipoCelda() == TipoCelda.PROVINCIA) {
		                capturarHorizontal = true;
		                celdasTipoProvinciaHorizontales = j;
		            }
		        }

		        if (capturarHorizontal && celdasTipoProvinciaHorizontales == 1) {
		            realizarCapturaEnProvincia(celdasContiguasHorizontal[0], captura, pieza);
		        }

		        if (capturarHorizontal && celdasTipoProvinciaHorizontales == 0) {
		            realizarCapturaEnProvincia(celdasContiguasHorizontal[1], captura, pieza);
		        }
		    }
		}

		/**
		 * Realiza la captura de una celda contigua en el área de provincias si se cumplen las condiciones.
		 * 
		 * @param celdaProvincia Celda de tipo provincia.
		 * @param captura Celda contigua a la celda de destino.
		 * @param pieza Pieza que se movió en el último movimiento.
		 */
		private void realizarCapturaEnProvincia(Celda celdaProvincia, Celda captura, Pieza pieza) {
		    if (celdaProvincia.consultarPieza() != null && celdaProvincia.consultarPieza().consultarTipoPieza() == pieza.consultarTipoPieza()) {
		        if (captura.consultarPieza() != null && captura.consultarPieza().consultarTipoPieza() != TipoPieza.REY) {
		            tablero.eliminarPieza(captura.consultarCoordenada());
		        }
		    }
		}

		/**
		 * Retrocede una jugada en el historial y restaura el estado del tablero y el turno.
		 */
		public void retroceder() {
		    if (numeroJugada > 0) {
		        numeroJugada--;
		        cambiarTurno();
		        // CLonar
		        Registro ultimoRegistro = historial.extraerUltimoRegistro();
		        ultimoMovimiento = ultimoRegistro.jugada();
		        tablero = ultimoRegistro.tablero();
		    }
		}

		
}
