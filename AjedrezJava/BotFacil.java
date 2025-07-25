public class BotFacil {

    public static int[] obtenerMovimiento(String[][] piezas) {
        for (int filaOrigen = 0; filaOrigen < 8; filaOrigen++) {
            for (int colOrigen = 0; colOrigen < 8; colOrigen++) {
                String pieza = piezas[filaOrigen][colOrigen];
                if (pieza != null && pieza.contains("negro")) {
                    for (int filaDestino = 0; filaDestino < 8; filaDestino++) {
                        for (int colDestino = 0; colDestino < 8; colDestino++) {
                            if (ValidadorMovimiento.esMovimientoValido(piezas, pieza, filaOrigen, colOrigen, filaDestino, colDestino)) {
                                return new int[]{filaOrigen, colOrigen, filaDestino, colDestino};
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
