import javax.swing.*;
import java.awt.*;

public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Menú Principal - Ajedrez");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(245, 245, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        JLabel titulo = new JLabel("Menú Principal");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Botón Iniciar Juego 1 vs 1
        JButton btnIniciar = new JButton("Iniciar Juego 1 vs 1");
        btnIniciar.setFont(new Font("Arial", Font.PLAIN, 18));
        btnIniciar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnIniciar.addActionListener(e -> {
            dispose();
            new TableroAjedrez().setVisible(true); // Juego normal
        });

        // Botón para mostrar opciones de Bot
        JButton btnJugarBot = new JButton("Jugar vs Bot");
        btnJugarBot.setFont(new Font("Arial", Font.PLAIN, 18));
        btnJugarBot.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subpanel oculto para botones de Bot Fácil y Difícil
        JPanel panelBotOpciones = new JPanel();
        panelBotOpciones.setLayout(new BoxLayout(panelBotOpciones, BoxLayout.Y_AXIS));
        panelBotOpciones.setBackground(new Color(245, 245, 245));
        panelBotOpciones.setVisible(false); // se muestra al presionar el botón principal

        JButton btnBotFacil = new JButton("Bot Fácil");
        btnBotFacil.setFont(new Font("Arial", Font.PLAIN, 16));
        btnBotFacil.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBotFacil.addActionListener(e -> {
            dispose();
            // Aquí abrirías el tablero con lógica de bot fácil
            JOptionPane.showMessageDialog(null, "Iniciando contra Bot Fácil (por implementar)");
        });

        JButton btnBotDificil = new JButton("Bot Difícil");
        btnBotDificil.setFont(new Font("Arial", Font.PLAIN, 16));
        btnBotDificil.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnBotDificil.addActionListener(e -> {
            dispose();
            // Aquí abrirías el tablero con lógica de bot difícil
            JOptionPane.showMessageDialog(null, "Iniciando contra Bot Difícil (por implementar)");
        });

        // Mostrar/ocultar submenú al hacer clic
        btnJugarBot.addActionListener(e -> {
            panelBotOpciones.setVisible(!panelBotOpciones.isVisible());
            pack(); // Redimensionar ventana automáticamente si se expande
        });

        // Botón Salir
        JButton btnSalir = new JButton("Salir");
        btnSalir.setFont(new Font("Arial", Font.PLAIN, 18));
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.addActionListener(e -> System.exit(0));

        // Agregar al panel principal
        panel.add(titulo);
        panel.add(Box.createVerticalStrut(30));
        panel.add(btnIniciar);
        panel.add(Box.createVerticalStrut(20));
        panel.add(btnJugarBot);
        panel.add(Box.createVerticalStrut(10));

        // Subopciones de bot
        panelBotOpciones.add(Box.createVerticalStrut(10));
        panelBotOpciones.add(btnBotFacil);
        panelBotOpciones.add(Box.createVerticalStrut(10));
        panelBotOpciones.add(btnBotDificil);
        panel.add(panelBotOpciones);

        panel.add(Box.createVerticalStrut(30));
        panel.add(btnSalir);

        add(panel);
    }
}
