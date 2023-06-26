// package src;
import javax.swing.JToggleButton;
import javax.speech.*;
import javax.speech.recognition.*;
import java.io.FileReader;
import java.util.Locale;
import javax.swing.JFrame;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.File;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JPanel;

public class Escucha extends ResultAdapter {
  
    static Recognizer recognizer;
    String gst;
    private MiPanel miPanel;
    private JFrame frame;
    private JFrame frameBotones;
    private boolean usoPorVoz = false;


    public Escucha() {
        miPanel = new MiPanel();
        frame = new JFrame("Mi figura");
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JToggleButton toggleButton = new JToggleButton("Modo voz", false);
        toggleButton.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    usoPorVoz = true;
                    miPanel.setUsoPorVoz(true);
                } else {
                    usoPorVoz = false;
                    miPanel.setUsoPorVoz(false);
                }
            }
        });
        miPanel.add(toggleButton);
        frame.add(miPanel);
        frame.setVisible(true);

         JFrame frame2 = new JFrame("Estadísticas");
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame2.setSize(500, 300);

        Estadisticas panel = new Estadisticas();
        frame2.add(panel);

        frame2.setVisible(true);
        
            // Bot�n para guardar el proyecto
        JButton guardarButton = new JButton("Guardar Proyecto");
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int seleccion = fileChooser.showSaveDialog(frame);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    guardarProyecto(file.getAbsolutePath());
                }
            }
        });

        // Bot�n para cargar el proyecto
        JButton abrirProyectoButton = new JButton("Abrir Proyecto");
        abrirProyectoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
        
            // Mostrar cuadro de di�logo para seleccionar el archivo a cargar
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(frameBotones);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                // Cargar el proyecto en el panel
                miPanel.abrirProyecto(file.getAbsolutePath());
            
                // Mostrar el frame principal para actualizar el proyecto cargado
                frame.setVisible(true);
                }
            frameBotones.dispose();
        }
        }); 

        // Crear un panel para los botones
        JPanel panelBotones = new JPanel();
        panelBotones.add(guardarButton);
        panelBotones.add(abrirProyectoButton);

        // Agregar el panel de botones y MiPanel al frame
        frame.add(panelBotones, BorderLayout.NORTH);
        frame.add(miPanel, BorderLayout.CENTER);

        frame.setVisible(true);
    }
    public void guardarProyecto(String nombreArchivo) {
        miPanel.guardarProyecto(nombreArchivo);
    }

    public void cargarProyecto(String nombreArchivo) {
        miPanel.abrirProyecto(nombreArchivo);
    }
    @Override
    public void resultAccepted(ResultEvent re) {
        if (usoPorVoz) {
            try {
                Result res = (Result) (re.getSource());
                ResultToken tokens[] = res.getBestTokens();

                String args[] = new String[1];
                args[0] = "";
                for (int i = 0; i < tokens.length; i++) {
                    gst = tokens[i].getSpokenText();
                    args[0] += gst + " ";
                    System.out.print(gst + " ");
                }
                System.out.println();
                if (gst.equals("1")) {
                    recognizer.deallocate();
                    args[0] = "1";
                    System.out.println(args[0]);
                    System.exit(0);
                } else {
                    recognizer.suspend();
                    recognizer.resume();
                }
                if (gst.equals("Caer")) {
                    miPanel.caer();
                }
                if (gst.equals("Triangulo")) {
                    miPanel.crearTriangulo();
                }
                if (gst.equals("Circulo")) {
                    miPanel.crearCirculo();
                }
                if (gst.equals("Rectangulo")) {
                    miPanel.crearRectangulo();
                }
               
                if (gst.equals("Eliminar")) {
                    miPanel.eliminarFigura();
                }
                if (gst.equals("Mover")) {
                    miPanel.mover();
                }
                if (gst.equals("Color")) {
                    miPanel.cambioColorArcoiris();
                }
                if (gst.equals("Blanca")) {
                    miPanel.crearCarta();
                }
                if (gst.equals("Moneda")) {
                    miPanel.crearMoneda();
                }
                if (gst.equals("Negra")) {
                    miPanel.crearCartaNegra();
                }
                if (gst.equals("Carta")) {
                    miPanel.crearCartaDadaVuelta();
                }
                if (gst.equals("Cuadrado")) {
                    miPanel.crearCuadrado();
                }
            } catch (Exception ex) {
                System.out.println("Ha ocurrido algo inesperado " + ex);
            }
        }
    }

    public static void main(String args[]) {
        try {
            recognizer = Central.createRecognizer(new EngineModeDesc(Locale.ROOT));
            recognizer.allocate();

            FileReader grammar1 = new FileReader("Gramatica.txt");

            RuleGrammar rg = recognizer.loadJSGF(grammar1);
            rg.setEnabled(true);

            Escucha escucha = new Escucha();
            recognizer.addResultListener(escucha);

            System.out.println("Empieze Dictado");
            recognizer.commitChanges();

            recognizer.requestFocus();
            recognizer.resume();
        } catch (Exception e) {
            System.out.println("Exception en " + e.toString());
            e.printStackTrace();
            System.exit(0);
        }
    }
}