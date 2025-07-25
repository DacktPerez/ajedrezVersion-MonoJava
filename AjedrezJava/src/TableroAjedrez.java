import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TableroAjedrez extends JFrame {

    private JLabel[][] celdas = new JLabel[8][8];
    private String[][] piezas = new String[8][8];

    private int filaOrigen = -1;
    private int colOrigen = -1;
    private JLabel celdaSeleccionada = null;

    private String turnoActual = "blanco";

    public TableroAjedrez() {
        setTitle("Tablero de Ajedrez");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panelTablero = new JPanel(new GridLayout(8, 8));

        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                JLabel celda = new JLabel();
                celda.setOpaque(true);
                celda.setHorizontalAlignment(SwingConstants.CENTER);
                celda.setVerticalAlignment(SwingConstants.CENTER);

                if ((fila + col) % 2 == 0) {
                    celda.setBackground(new Color(240, 217, 181));
                } else {
                    celda.setBackground(new Color(181, 136, 99));
                }

                final int f = fila;
                final int c = col;
                celda.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        manejarClick(f, c);
                    }
                });

                celdas[fila][col] = celda;
                panelTablero.add(celda);
            }
        }

        add(panelTablero);
        inicializarPiezas();
    }

    private void inicializarPiezas() {
        for (int col = 0; col < 8; col++) {
            colocar("resources/peon_blanco.png", 6, col);
            colocar("resources/peon_negro.png", 1, col);
        }
        colocar("resources/torre_blanco.png", 7, 0);
        colocar("resources/torre_blanco.png", 7, 7);
        colocar("resources/torre_negro.png", 0, 0);
        colocar("resources/torre_negro.png", 0, 7);

        colocar("resources/caballo_blanco.png", 7, 1);
        colocar("resources/caballo_blanco.png", 7, 6);
        colocar("resources/caballo_negro.png", 0, 1);
        colocar("resources/caballo_negro.png", 0, 6);

        colocar("resources/alfil_blanco.png", 7, 2);
        colocar("resources/alfil_blanco.png", 7, 5);
        colocar("resources/alfil_negro.png", 0, 2);
        colocar("resources/alfil_negro.png", 0, 5);

        colocar("resources/reina_blanco.png", 7, 3);
        colocar("resources/reina_negro.png", 0, 3);

        colocar("resources/rey_blanco.png", 7, 4);
        colocar("resources/rey_negro.png", 0, 4);
    }

    private void colocar(String nombreImagen, int fila, int columna) {
        piezas[fila][columna] = nombreImagen;
        ImageIcon icono = new ImageIcon(nombreImagen);
        Image imagen = icono.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        celdas[fila][columna].setIcon(new ImageIcon(imagen));
    }

    private void limpiar(int fila, int columna) {
        piezas[fila][columna] = null;
        celdas[fila][columna].setIcon(null);
    }

    private void manejarClick(int fila, int columna) {
        String piezaClic = piezas[fila][columna];

        if (filaOrigen == -1) {
            if (piezaClic == null) {
                JOptionPane.showMessageDialog(this, "No hay pieza en esta casilla. Selecciona una pieza para mover.");
                return;
            }
            String colorPieza = obtenerColor(piezaClic);
            if (!colorPieza.equals(turnoActual)) {
                JOptionPane.showMessageDialog(this, "No es tu turno.");
                return;
            }

            filaOrigen = fila;
            colOrigen = columna;
            if (celdaSeleccionada != null) {
                celdaSeleccionada.setBorder(null);
            }
            celdaSeleccionada = celdas[fila][columna];
            celdaSeleccionada.setBorder(new LineBorder(Color.BLUE, 3));
        } else {
            if (fila == filaOrigen && columna == colOrigen) {
                if (celdaSeleccionada != null) {
                    celdaSeleccionada.setBorder(null);
                    celdaSeleccionada = null;
                }
                filaOrigen = -1;
                colOrigen = -1;
                return;
            }

            if (piezaClic != null) {
                String colorPiezaClic = obtenerColor(piezaClic);
                if (colorPiezaClic.equals(turnoActual)) {
                    if (celdaSeleccionada != null) {
                        celdaSeleccionada.setBorder(null);
                    }
                    filaOrigen = fila;
                    colOrigen = columna;
                    celdaSeleccionada = celdas[fila][columna];
                    celdaSeleccionada.setBorder(new LineBorder(Color.BLUE, 3));
                    return;
                }
            }

            String piezaSeleccionada = piezas[filaOrigen][colOrigen];
            if (ValidadorMovimiento.esMovimientoValido(piezas, piezaSeleccionada, filaOrigen, colOrigen, fila, columna)) {
                String[][] copia = copiarMatriz(piezas);
                copia[fila][columna] = piezaSeleccionada;
                copia[filaOrigen][colOrigen] = null;

                if (piezaSeleccionada.contains("rey") && estaEnJaque(copia, turnoActual)) {
                    JOptionPane.showMessageDialog(this, "El rey no puede moverse a una casilla en jaque.");
                    return;
                }

                colocar(piezaSeleccionada, fila, columna);
                limpiar(filaOrigen, colOrigen);

                if (celdaSeleccionada != null) {
                    celdaSeleccionada.setBorder(null);
                    celdaSeleccionada = null;
                }
                filaOrigen = -1;
                colOrigen = -1;

                turnoActual = turnoActual.equals("blanco") ? "negro" : "blanco";

                if (estaEnJaque(piezas, turnoActual)) {
                    if (esJaqueMate(turnoActual)) {
                        JOptionPane.showMessageDialog(this, "¡Jaque mate! Ganó el jugador " + (turnoActual.equals("blanco") ? "negro" : "blanco") + " 🎉");
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(this, "¡Jaque al jugador " + turnoActual + "!");
                    }
                }

            } else {
                JOptionPane.showMessageDialog(this, "Movimiento inválido, intenta de nuevo.");
            }
        }
    }

    private String obtenerColor(String nombre) {
        return nombre.contains("blanco") ? "blanco" : "negro";
    }

    private boolean estaEnJaque(String[][] tablero, String colorDelRey) {
        int filaRey = -1, colRey = -1;
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                String pieza = tablero[fila][col];
                if (pieza != null && pieza.contains("rey") && pieza.contains(colorDelRey)) {
                    filaRey = fila;
                    colRey = col;
                }
            }
        }

        if (filaRey == -1 || colRey == -1) return true;

        String colorEnemigo = colorDelRey.equals("blanco") ? "negro" : "blanco";
        for (int fila = 0; fila < 8; fila++) {
            for (int col = 0; col < 8; col++) {
                String pieza = tablero[fila][col];
                if (pieza != null && pieza.contains(colorEnemigo)) {
                    if (ValidadorMovimiento.esMovimientoValido(tablero, pieza, fila, col, filaRey, colRey)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private boolean esJaqueMate(String color) {
        if (!estaEnJaque(piezas, color)) {
            return false; // No es jaque, así que no puede ser jaque mate
        }

        for (int filaInicio = 0; filaInicio < 8; filaInicio++) {
            for (int colInicio = 0; colInicio < 8; colInicio++) {
                String pieza = piezas[filaInicio][colInicio];
                if (pieza != null && pieza.contains(color)) {
                    for (int filaFin = 0; filaFin < 8; filaFin++) {
                        for (int colFin = 0; colFin < 8; colFin++) {
                            // Validar si el movimiento es válido según las reglas
                            if (ValidadorMovimiento.esMovimientoValido(piezas, pieza, filaInicio, colInicio, filaFin, colFin)) {
                                // Simular el movimiento
                                String[][] copia = copiarMatriz(piezas);
                                copia[filaFin][colFin] = pieza;
                                copia[filaInicio][colInicio] = null;

                                // Si después del movimiento el rey ya no está en jaque, no es jaque mate
                                if (!estaEnJaque(copia, color)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true; // Si ningún movimiento elimina el jaque, es jaque mate
    }

    private String[][] copiarMatriz(String[][] original) {
        String[][] copia = new String[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(original[i], 0, copia[i], 0, 8);
        }
        return copia;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TableroAjedrez().setVisible(true);
            }
        });
    }
}
