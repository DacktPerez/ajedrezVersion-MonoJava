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
    private boolean contraBot = false;
    
    // Variables para controlar el enroque
    private boolean reyBlancoMovido = false;
    private boolean reyNegroMovido = false;
    private boolean torreBlancaIzquierdaMovida = false;
    private boolean torreBlancaDerechaMovida = false;
    private boolean torreNegraIzquierdaMovida = false;
    private boolean torreNegraDerechaMovida = false;

    // ✅ Constructor con opción de juego contra bot o 1 vs 1
    public TableroAjedrez(boolean contraBot) {
        this.contraBot = contraBot;

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
        if (contraBot && turnoActual.equals("negro")) return; // Bloquear input humano si juega el bot

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
            
            // Verificar si es un movimiento de enroque
            if (esMovimientoEnroque(filaOrigen, colOrigen, fila, columna)) {
                boolean esEnroqueCorto = (columna == 6); // columna 6 es enroque corto, columna 2 es enroque largo
                
                if (puedeHacerEnroque(turnoActual, esEnroqueCorto)) {
                    realizarEnroque(turnoActual, esEnroqueCorto);
                    
                    if (celdaSeleccionada != null) {
                        celdaSeleccionada.setBorder(null);
                        celdaSeleccionada = null;
                    }
                    filaOrigen = -1;
                    colOrigen = -1;
                    
                    turnoActual = turnoActual.equals("blanco") ? "negro" : "blanco";
                    
                    // Verificar jaque después del enroque
                    if (estaEnJaque(piezas, turnoActual)) {
                        if (esJaqueMate(turnoActual)) {
                            JOptionPane.showMessageDialog(this, "¡Jaque mate! Ganó el jugador " + (turnoActual.equals("blanco") ? "negro" : "blanco") + " 🎉");
                            System.exit(0);
                        } else {
                            JOptionPane.showMessageDialog(this, "¡Jaque al jugador " + turnoActual + "!");
                        }
                    }
                    
                    // ✅ Lógica del bot sencillo
                    if (contraBot && turnoActual.equals("negro")) {
                        realizarMovimientoBot();
                    }
                    return;
                } else {
                    JOptionPane.showMessageDialog(this, "No se puede realizar el enroque en este momento.");
                    return;
                }
            }
            
            if (ValidadorMovimiento.esMovimientoValido(piezas, piezaSeleccionada, filaOrigen, colOrigen, fila, columna)) {
                String[][] copia = copiarMatriz(piezas);
                copia[fila][columna] = piezaSeleccionada;
                copia[filaOrigen][colOrigen] = null;

                if (piezaSeleccionada.contains("rey") && estaEnJaque(copia, turnoActual)) {
                    JOptionPane.showMessageDialog(this, "El rey no puede moverse a una casilla en jaque.");
                    return;
                }

                // Actualizar el estado de las piezas movidas antes de realizar el movimiento
                actualizarEstadoPiezasMovidas(filaOrigen, colOrigen, piezaSeleccionada);

                colocar(piezaSeleccionada, fila, columna);
                limpiar(filaOrigen, colOrigen);

                // Verificar si el peón debe ser coronado
                if (debeCoronarPeon(piezaSeleccionada, fila)) {
                    String colorPieza = obtenerColor(piezaSeleccionada);
                    coronarPeon(fila, columna, colorPieza);
                }

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

                // ✅ Lógica del bot sencillo
                if (contraBot && turnoActual.equals("negro")) {
                    realizarMovimientoBot();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Movimiento inválido, intenta de nuevo.");
            }
        }
    }

    private void realizarMovimientoBot() {
        try {
            Thread.sleep(500); // 🧠 pausa para efecto visual

            int[] mov = BotFacil.obtenerMovimiento(piezas);
            if (mov != null) {
                String pieza = piezas[mov[0]][mov[1]];
                
                // Verificar si el bot quiere hacer enroque
                if (esMovimientoEnroque(mov[0], mov[1], mov[2], mov[3])) {
                    boolean esEnroqueCorto = (mov[3] == 6);
                    if (puedeHacerEnroque("negro", esEnroqueCorto)) {
                        realizarEnroque("negro", esEnroqueCorto);
                    } else {
                        // Si no puede hacer enroque, hacer movimiento normal
                        actualizarEstadoPiezasMovidas(mov[0], mov[1], pieza);
                        colocar(pieza, mov[2], mov[3]);
                        limpiar(mov[0], mov[1]);
                    }
                } else {
                    // Movimiento normal del bot
                    actualizarEstadoPiezasMovidas(mov[0], mov[1], pieza);
                    colocar(pieza, mov[2], mov[3]);
                    limpiar(mov[0], mov[1]);
                    
                    // Verificar si el peón del bot debe ser coronado
                    if (debeCoronarPeon(pieza, mov[2])) {
                        String colorPieza = obtenerColor(pieza);
                        coronarPeonBot(mov[2], mov[3], colorPieza);
                    }
                }
                
                turnoActual = "blanco";

                if (estaEnJaque(piezas, turnoActual)) {
                    if (esJaqueMate(turnoActual)) {
                        JOptionPane.showMessageDialog(this, "¡Jaque mate! Ganó el bot.");
                        System.exit(0);
                    } else {
                        JOptionPane.showMessageDialog(this, "¡Jaque al jugador blanco!");
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
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
        if (!estaEnJaque(piezas, color)) return false;

        for (int filaInicio = 0; filaInicio < 8; filaInicio++) {
            for (int colInicio = 0; colInicio < 8; colInicio++) {
                String pieza = piezas[filaInicio][colInicio];
                if (pieza != null && pieza.contains(color)) {
                    for (int filaFin = 0; filaFin < 8; filaFin++) {
                        for (int colFin = 0; colFin < 8; colFin++) {
                            if (ValidadorMovimiento.esMovimientoValido(piezas, pieza, filaInicio, colInicio, filaFin, colFin)) {
                                String[][] copia = copiarMatriz(piezas);
                                copia[filaFin][colFin] = pieza;
                                copia[filaInicio][colInicio] = null;
                                if (!estaEnJaque(copia, color)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }

        return true;
    }

    private String[][] copiarMatriz(String[][] original) {
        String[][] copia = new String[8][8];
        for (int i = 0; i < 8; i++) {
            System.arraycopy(original[i], 0, copia[i], 0, 8);
        }
        return copia;
    }

    // Función para validar si el enroque es posible
    private boolean puedeHacerEnroque(String color, boolean esEnroqueCorto) {
        int fila = color.equals("blanco") ? 7 : 0;
        int colRey = 4;
        int colTorre = esEnroqueCorto ? 7 : 0;
        
        // Verificar que el rey y la torre no se hayan movido
        if (color.equals("blanco")) {
            if (reyBlancoMovido) return false;
            if (esEnroqueCorto && torreBlancaDerechaMovida) return false;
            if (!esEnroqueCorto && torreBlancaIzquierdaMovida) return false;
        } else {
            if (reyNegroMovido) return false;
            if (esEnroqueCorto && torreNegraDerechaMovida) return false;
            if (!esEnroqueCorto && torreNegraIzquierdaMovida) return false;
        }
        
        // Verificar que las piezas estén en sus posiciones iniciales
        String rey = piezas[fila][colRey];
        String torre = piezas[fila][colTorre];
        
        if (rey == null || !rey.contains("rey") || !rey.contains(color)) return false;
        if (torre == null || !torre.contains("torre") || !torre.contains(color)) return false;
        
        // Verificar que no haya piezas entre el rey y la torre
        int inicio = Math.min(colRey, colTorre) + 1;
        int fin = Math.max(colRey, colTorre);
        
        for (int col = inicio; col < fin; col++) {
            if (piezas[fila][col] != null) {
                return false;
            }
        }
        
        // Verificar que el rey no esté en jaque
        if (estaEnJaque(piezas, color)) {
            return false;
        }
        
        // Verificar que el rey no pase por casillas atacadas
        int direccion = esEnroqueCorto ? 1 : -1;
        for (int i = 1; i <= 2; i++) {
            int colIntermedia = colRey + (i * direccion);
            
            // Crear tablero temporal para simular el movimiento
            String[][] tableroTemporal = copiarMatriz(piezas);
            tableroTemporal[fila][colIntermedia] = rey;
            tableroTemporal[fila][colRey] = null;
            
            if (estaEnJaque(tableroTemporal, color)) {
                return false;
            }
        }
        
        return true;
    }
    
    // Función para realizar el enroque
    private void realizarEnroque(String color, boolean esEnroqueCorto) {
        int fila = color.equals("blanco") ? 7 : 0;
        int colRey = 4;
        int colTorre = esEnroqueCorto ? 7 : 0;
        int nuevaColRey = esEnroqueCorto ? 6 : 2;
        int nuevaColTorre = esEnroqueCorto ? 5 : 3;
        
        String rey = piezas[fila][colRey];
        String torre = piezas[fila][colTorre];
        
        // Mover el rey
        colocar(rey, fila, nuevaColRey);
        limpiar(fila, colRey);
        
        // Mover la torre
        colocar(torre, fila, nuevaColTorre);
        limpiar(fila, colTorre);
        
        // Marcar que las piezas se movieron
        if (color.equals("blanco")) {
            reyBlancoMovido = true;
            if (esEnroqueCorto) {
                torreBlancaDerechaMovida = true;
            } else {
                torreBlancaIzquierdaMovida = true;
            }
        } else {
            reyNegroMovido = true;
            if (esEnroqueCorto) {
                torreNegraDerechaMovida = true;
            } else {
                torreNegraIzquierdaMovida = true;
            }
        }
    }
    
    // Función para detectar si un movimiento es un enroque
    private boolean esMovimientoEnroque(int filaOrigen, int colOrigen, int filaDestino, int colDestino) {
        // Solo el rey puede iniciar un enroque
        String pieza = piezas[filaOrigen][colOrigen];
        if (pieza == null || !pieza.contains("rey")) {
            return false;
        }
        
        // El enroque solo se puede hacer en la misma fila
        if (filaOrigen != filaDestino) {
            return false;
        }
        
        // Verificar si es enroque corto (rey se mueve 2 casillas a la derecha)
        if (colOrigen == 4 && colDestino == 6) {
            return true; // Enroque corto
        }
        
        // Verificar si es enroque largo (rey se mueve 2 casillas a la izquierda)
        if (colOrigen == 4 && colDestino == 2) {
            return true; // Enroque largo
        }
        
        return false;
    }
    
    // Función para actualizar el estado de las piezas movidas
    private void actualizarEstadoPiezasMovidas(int fila, int columna, String pieza) {
        if (pieza.contains("rey")) {
            if (pieza.contains("blanco")) {
                reyBlancoMovido = true;
            } else {
                reyNegroMovido = true;
            }
        } else if (pieza.contains("torre")) {
            if (pieza.contains("blanco")) {
                if (fila == 7 && columna == 0) {
                    torreBlancaIzquierdaMovida = true;
                } else if (fila == 7 && columna == 7) {
                    torreBlancaDerechaMovida = true;
                }
            } else {
                if (fila == 0 && columna == 0) {
                    torreNegraIzquierdaMovida = true;
                } else if (fila == 0 && columna == 7) {
                    torreNegraDerechaMovida = true;
                }
            }
        }
    }
    
    // Función para verificar si un peón debe ser coronado
    private boolean debeCoronarPeon(String pieza, int filaDestino) {
        if (pieza == null || !pieza.contains("peon")) {
            return false;
        }
        
        // Peón blanco llega a la fila 0 (primera fila)
        if (pieza.contains("blanco") && filaDestino == 0) {
            return true;
        }
        
        // Peón negro llega a la fila 7 (última fila)
        if (pieza.contains("negro") && filaDestino == 7) {
            return true;
        }
        
        return false;
    }
    
    // Función para coronar un peón (jugador humano)
    private void coronarPeon(int fila, int columna, String color) {
        // Opciones de coronación
        String[] opciones = {"Reina", "Torre", "Alfil", "Caballo"};
        
        // Crear panel personalizado para mostrar las opciones
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("¡Coronación de peón! Elige la nueva pieza:"));
        panel.add(Box.createVerticalStrut(10));
        
        int seleccion = JOptionPane.showOptionDialog(
            this,
            panel,
            "Coronación de Peón",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opciones,
            opciones[0] // Por defecto, reina
        );
        
        // Si el usuario cierra el diálogo sin seleccionar, coronar como reina por defecto
        if (seleccion == -1) {
            seleccion = 0;
        }
        
        // Determinar la nueva pieza basada en la selección
        String nuevaPieza;
        switch (seleccion) {
            case 0: // Reina
                nuevaPieza = color.equals("blanco") ? "resources/reina_blanco.png" : "resources/reina_negro.png";
                break;
            case 1: // Torre
                nuevaPieza = color.equals("blanco") ? "resources/torre_blanco.png" : "resources/torre_negro.png";
                break;
            case 2: // Alfil
                nuevaPieza = color.equals("blanco") ? "resources/alfil_blanco.png" : "resources/alfil_negro.png";
                break;
            case 3: // Caballo
                nuevaPieza = color.equals("blanco") ? "resources/caballo_blanco.png" : "resources/caballo_negro.png";
                break;
            default:
                nuevaPieza = color.equals("blanco") ? "resources/reina_blanco.png" : "resources/reina_negro.png";
                break;
        }
        
        // Colocar la nueva pieza
        colocar(nuevaPieza, fila, columna);
        
        // Mostrar mensaje de confirmación
        String nombrePieza = opciones[seleccion].toLowerCase();
        JOptionPane.showMessageDialog(this, 
            "¡Peón coronado como " + nombrePieza + "!", 
            "Coronación exitosa", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Función para coronar un peón automáticamente (para el bot)
    private void coronarPeonBot(int fila, int columna, String color) {
        // El bot siempre elige reina (la pieza más poderosa)
        String nuevaPieza = color.equals("blanco") ? "resources/reina_blanco.png" : "resources/reina_negro.png";
        colocar(nuevaPieza, fila, columna);
        
        // Mostrar mensaje informativo
        JOptionPane.showMessageDialog(this, 
            "El bot ha coronado su peón como reina.", 
            "Coronación del bot", 
            JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        // Prueba modo 1 vs 1
        SwingUtilities.invokeLater(() -> new TableroAjedrez(false).setVisible(true));
        // Prueba vs bot (descomenta para test)
        // SwingUtilities.invokeLater(() -> new TableroAjedrez(true).setVisible(true));
    }
}
