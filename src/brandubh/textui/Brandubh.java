package brandubh.textui;

import java.util.Scanner;

import brandubh.control.Arbitro;
import brandubh.modelo.Celda;
import brandubh.modelo.Jugada;
import brandubh.modelo.Tablero;
import brandubh.util.Color;
import brandubh.util.Coordenada;
import brandubh.util.Traductor;

/**
 * Brandubh en modo texto.
 * 
 * Se abusa del uso del modificador static tanto en atributos como en métodos
 * para comprobar su similitud a variables globales y funciones globales de
 * otros lenguajes.
 * 
 * La programación en este código sigue más el paradigma de programación
 * estructurada en mayor medida que la orientación a objetos.
 * 
 * En algunoss casos los métodos estáticos son meros envoltorios o "wrappers"
 * de invocaciones a métodos del árbitro.
 *
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @since 1.0
 * @version 2.0
 * @see brandubh.modelo
 * @see brandubh.control
 * @see brandubh.util
 */
public class Brandubh {

	/** Tamaño en caracteres de una jugada. */
	private static final int TAMAÑO_JUGADA = 4;

	/** Posición en el texto de una jugada de la coordenada destino. */
	private static final int INICIO_COORDENADA_DESTINO = 2;

	/** Texto para interrumpir la partida. */
	private static final String TEXTO_SALIR = "salir";	
	
	/** Texto para retroceder la última jugada. */
	private static final String TEXTO_RETROCEDER = "retroceder";

	/** Árbitro. */
	private static Arbitro arbitro;

	/** Lector por teclado. */
	private static Scanner scanner;

	/** Oculta el constructor por defecto. */
	private Brandubh() {
	}

	/**
	 * Método raíz con el algoritmo principal en modo texto.
	 * 
	 * @param args argumentos de entrada en línea de comandos
	 */
	public static void main(String[] args) {
	    // Inicializar la partida
	    inicializarPartida();
	    
	    // Mostrar mensaje de bienvenida
	    mostrarMensajeBienvenida();
	    
	    // Bucle principal del juego
	    boolean salir = false;
	    while (!salir) {
	        // Mostrar el estado actual del tablero
	        mostrarTablero();
	        
	        // Solicitar al jugador introducir una jugada
	        String textoJugada = recogerTextoDeJugadaPorTeclado();
	        
	        // Comprobar si el jugador quiere salir
	        if (comprobarSalir(textoJugada)) {
	            salir = true; // Salir del bucle si el jugador desea salir
	        } else {
	            // Comprobar si el jugador quiere retroceder la última jugada
	            if (comprobarDeshacer(textoJugada)) {
	                retrocederUltimaJugada();
	            } else {
	                // Validar el formato de la jugada
	                if (!validarFormato(textoJugada)) {
	                    // Mostrar mensaje de error en formato de entrada
	                    mostrarErrorEnFormatoDeEntrada();
	                } else {
	                    // Extraer la jugada a partir del texto introducido
	                    Jugada jugada = extraerJugada(textoJugada);
	                    
	                    // Comprobar si la jugada es legal
	                    if (!esLegal(jugada)) {
	                        // Mostrar mensaje de error por movimiento ilegal
	                        mostrarErrorPorMovimientoIlegal(textoJugada);
	                    } else {
	                        // Realizar el movimiento en el juego
	                        realizarMovimiento(jugada);
	                        
	                        // Realizar las capturas correspondientes al último movimiento
	                        realizarCapturas(jugada);
	                        
	                        // Cambiar el turno
	                        cambiarTurnoPartida();
	                        
	                        // Comprobar si la partida ha finalizado
	                        if (comprobarFinalizacionPartida()) {
	                            // Mostrar el ganador y salir del bucle
	                            mostrarGanador();
	                            salir = true;
	                        }
	                    }
	                }
	            }
	        }
	    }
	    
	    // Finalizar la partida cerrando recursos
	    finalizarPartida();
	}

	
	/**
	 * Comprueba si se quiere deshacer la última jugada.
	 * 
	 * @param jugada jugada en formato texto
	 * @return true si el usuario introduce deshacer, false en caso contrario
	 * @since 2.0
	 */
	private static boolean comprobarDeshacer(String jugada) {
		return jugada.equalsIgnoreCase(TEXTO_RETROCEDER);
	}	

	/**
	 * Inicializa el estado de los elementos de la partida.
	 * 
	 * @since 2.0
	 */
	private static void inicializarPartida() {
		// Inicializaciones
		arbitro = new Arbitro(new Tablero());
		// Cargar piezas con la configuración inicial...
		arbitro.colocarPiezasConfiguracionInicial();
		// Abrir la lectura desde teclado...
		scanner = new Scanner(System.in);
	}
	
	/**
	 * Recoge el texto de la jugada por teclado.
	 * 
	 * @return jugada jugada en formato texto
	 */
	private static String recogerTextoDeJugadaPorTeclado() {
		System.out
				.print("Introduce jugada turno con piezas de color " + arbitro.consultarTurno() + " (formato cfcf): ");
		return scanner.next();
	}
	

	/**
	 * Comprueba si se quiere finalizar la partida por parte de los usuarios.
	 * 
	 * @param jugada jugada en formato texto
	 * @return true si el usuario introduce salir, false en caso contrario
	 */
	private static boolean comprobarSalir(String jugada) {
		return jugada.equalsIgnoreCase(TEXTO_SALIR);
	}	
	
	/**
	 * Valida la corrección del formato de la jugada. Solo comprueba la corrección
	 * del formato de entrada en cuanto al tablero, no la validez de la jugada en
	 * cuanto a las reglas del juego.
	 * 
	 * La jugada tiene que tener cuatro caracteres y contener letras y números de
	 * acuerdo a las reglas de la notación algebraica.
	 * 
	 * Otra mejor solución alternativa es el uso de expresiones regulares (se verán
	 * en la asignatura de 3º Procesadores del Lenguaje).
	 * 
	 * @param textoJugada a validar
	 * @return true si el formato de la jugada es correcta según las coordenadas
	 *         disponibles del tablero
	 */
	private static boolean validarFormato(String textoJugada) {
		if (textoJugada.length() == TAMAÑO_JUGADA) {
			String origen = textoJugada.substring(0, INICIO_COORDENADA_DESTINO);
			String destino = textoJugada.substring(INICIO_COORDENADA_DESTINO, TAMAÑO_JUGADA);
			// comprobar si ambos textos son correctos
			return Traductor.esTextoCorrectoParaCoordenada(origen) && Traductor.esTextoCorrectoParaCoordenada(destino);
		}
		return false;
	}
	
	/**
	 * Extrae la jugada a partir del texto introducido por teclado.
	 * 
	 * @param jugadaTexto texto con la jugada
	 * @return jugada
	 * @see #extraerCoordenada(String, int, int)
	 * @since 2.0
	 */
	private static Jugada extraerJugada(String jugadaTexto) {
		Coordenada coordenadaOrigen = extraerCoordenada(jugadaTexto, 0, INICIO_COORDENADA_DESTINO);
		Coordenada coordenadaDestino = extraerCoordenada(jugadaTexto, INICIO_COORDENADA_DESTINO, TAMAÑO_JUGADA);
		Celda origen = arbitro.consultarTablero().consultarCelda(coordenadaOrigen);
		Celda destino = arbitro.consultarTablero().consultarCelda(coordenadaDestino);
		return new Jugada(origen, destino);
	}	

	/**
	 * Extrae una coordenada a partir del texto de entrada y de las posiciones
	 * [incio, fin) indicadas.
	 * 
	 * Dada una jugada en texto, extraerá la coordenada origen o destino, en función
	 * de la posición de inicio y fin dada.
	 * 
	 * @param jugada texto en formato notación algebraica (e.g. a1a3)
	 * @param inicio posición en el texto a partir del cual leer
	 * @param fin    posición final - 1, hasta donde leer el texto
	 * @return coordenada o null, si no es posible extraerla
	 */
	private static Coordenada extraerCoordenada(String jugada, int inicio, int fin) {
		if (jugada.length() != TAMAÑO_JUGADA)
			return null;
		String textoExtraido = jugada.substring(inicio, fin);
		return Traductor.consultarCoordenadaParaNotacionAlgebraica(textoExtraido);
	}
	
	/**
	 * Comprueba la legalidad de la jugada.
	 * 
	 * @param jugada jugada
	 * @return true si es legal, false en caso contrario
	 */
	private static boolean esLegal(Jugada jugada) {
		return arbitro.esMovimientoLegal(jugada);
	}

	/**
	 * Realizar la jugada completando el movimientos.
	 * 
	 * @param jugada jugada
	 * @since 2.0
	 */
	private static void realizarMovimiento(Jugada jugada) {
		arbitro.mover(jugada);
	}	
	
	/**
	 * Deshacer la última jugada.
	 * 
	 * @since 2.0
	 */
	private static void retrocederUltimaJugada() {
		arbitro.retroceder();
	}	
	
	
	/**
	 * Realizar las capturas correspondienes al último movimiento.
	 * 
	 * @param jugada jugada
	 * @since 2.0
	 */
	private static void realizarCapturas(Jugada jugada) {
		arbitro.realizarCapturasTrasMover();
	}	

	/**
	 * Cambia el turno de la partida.
	 */
	private static void cambiarTurnoPartida() {
		arbitro.cambiarTurno();
	}
		
	/**
	 * Comprueba si está finalizada la partida.
	 * 
	 * @return true si hay victoria de atacante o defensor, false en caso contrario
	 */
	private static boolean comprobarFinalizacionPartida() {
		return arbitro.haGanadoAtacante() || arbitro.haGanadoRey();
	}		
	
	/**
	 * Finaliza la partida cerrando recursos abiertos como el teclado.
	 */
	private static void finalizarPartida() {
		System.out.println("Partida finalizada.");
		scanner.close();
	}		
	
	/**
	 * Muestra el mensaje de bienvenida con instrucciones para finalizar la partida.
	 */
	private static void mostrarMensajeBienvenida() {
		System.out.println("Bienvenido al juego del Brandubh 2.0");
		System.out.println(
				"Atacan piezas de color " + Color.NEGRO + " y defienden piezas de color " + Color.BLANCO + ".");
		System.out.println("Para interrumpir partida introduzca \"salir\".");
		System.out.println("Para retroceder el estado a la anterior jugada introduzca \"retroceder\".");
		System.out.println("Disfrute de la partida...");
	}

	/**
	 * Muestra la información de error en el formato de entrada, mostrando ejemplos.
	 */
	private static void mostrarErrorEnFormatoDeEntrada() {
		System.out.println();
		System.out.println("Error en el formato de entrada.");
		System.out.println(
				"El formato debe ser letranumeroletranumero, por ejemplo a7a5 o g2e2, o bien introducir la cadena \"salir\" para finalizar la partida.");
		System.out.println("Las letras deben estar en el rango [a,g] y los números en el rango [1,7].");
	}
	
	/**
	 * Informa de la ilegalidad de la jugada intentada.
	 * 
	 * @param textoJugada texto de la jugada introducido por teclado
	 */
	private static void mostrarErrorPorMovimientoIlegal(String textoJugada) {
		System.out.printf("%nLa jugada %s es ilegal.%nRevise las reglas del juego.%n", textoJugada);
	}

	/**
	 * Muestra el estado del tablero con sus piezas actuales en pantalla.
	 * 
	 * @since 2.0
	 */
	private static void mostrarTablero() {
		System.out.println();
		System.out.println(arbitro.consultarTablero().aTexto());
	}

	/**
	 * Muestra el ganador de la partida en pantalla.
	 */
	private static void mostrarGanador() {
		if (arbitro.haGanadoAtacante()) {
			System.out.printf("%nHa ganado la partida el jugador atacante con piezas de color %s.%n",
					arbitro.consultarTurno());
		} else if (arbitro.haGanadoRey()) {
			System.out.printf("%nHa ganado la partida el jugador defensor con piezas de color %s.%n",
					arbitro.consultarTurno());
		} else {
			System.out.println("\nNo hay ganador.");
		}
	}
}
