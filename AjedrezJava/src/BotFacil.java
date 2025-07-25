import java.util.Random;

public class BotFacil {
    private static final Random RAND = new Random();

    /**
     * Recorre todas las piezas negras y, usando reservoir sampling,
     * selecciona uniformemente un movimiento válido sin almacenar todos.
     * @param piezas Estado actual del tablero
     * @return int[]{filaOrigen, colOrigen, filaDestino, colDestino} o null si no hay movimientos
     */
    public static int[] obtenerMovimiento(String[][] piezas) {
        int[] movimientoSeleccionado = null;
        int conteoMovimientos = 0;

        for (int filaO = 0; filaO < 8; filaO++) {
            for (int colO = 0; colO < 8; colO++) {
                String pieza = piezas[filaO][colO];
                if (pieza != null && pieza.contains("negro")) {
                    for (int filaD = 0; filaD < 8; filaD++) {
                        for (int colD = 0; colD < 8; colD++) {
                            if (ValidadorMovimiento.esMovimientoValido(piezas, pieza, filaO, colO, filaD, colD)) {
                                conteoMovimientos++;
                                // Con probabilidad 1/conteoMovimientos, reemplazo la elección anterior
                                if (RAND.nextInt(conteoMovimientos) == 0) {
                                    movimientoSeleccionado = new int[]{filaO, colO, filaD, colD};
                                }
                            }
                        }
                    }
                }
            }
        }

        return movimientoSeleccionado;
    }
}
