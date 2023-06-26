// package src;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import manipulacionPaquete.Libreria;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

public class MiPanel extends JPanel {

    private ArrayList<Figura> figuras = new ArrayList<>();
    private ArrayList<ArrayList<Figura>> figurasSec = new ArrayList<>();
    private String lugarDeApraricion = "400";
    private Figura figuraSeleccionada;
    private Point posicionRaton;
    private JPopupMenu menuEmergente;
    private Color colorSeleccionado;
    Timer Temporizador;

    protected boolean usoPorVoz = false;

    public void setUsoPorVoz(boolean usoPorVoz) {
        this.usoPorVoz = usoPorVoz;
    }

    // Crear etiquetas para mostrar los datos de la figura seleccionada
    JLabel tipoFiguraLabel = new JLabel("Tipo de figura:");
    JLabel posicionXLabel = new JLabel("Posicion X:");
    JLabel posicionYLabel = new JLabel("Posicion Y:");

    JLabel colorLabel = new JLabel("Color:");
    Color pokerTableGreen = new Color(0, 102, 0);

    public MiPanel() {

        setBackground(pokerTableGreen);
        setPreferredSize(new Dimension(600, 600));
        figuras = new ArrayList<>();

        // Agregar etiquetas al panel
        add(tipoFiguraLabel);
        add(posicionXLabel);
        add(posicionYLabel);

        tipoFiguraLabel.setBounds(10, 10, 100, 20);
        posicionXLabel.setBounds(10, 30, 100, 20);
        posicionYLabel.setBounds(10, 50, 100, 20);

        // Creamos el men� emergente

        menuEmergente = new JPopupMenu();

        JMenuItem cuadradoItem = new JMenuItem("Cuadrado");
        cuadradoItem.addActionListener(e -> {
            crearCuadrado();
        });

        JMenuItem rectanguloItem = new JMenuItem("Rectngulo");
        rectanguloItem.addActionListener(e -> {
            crearRectangulo();
        });

        JMenuItem textItem = new JMenuItem("Texto");
        textItem.addActionListener(e -> {
            crearTexto();
        });

        JMenuItem circuloItem = new JMenuItem("Circulo");
        circuloItem.addActionListener(e -> {
            crearCirculo();
        });

        JMenuItem trianguloItem = new JMenuItem("tringulo");
        trianguloItem.addActionListener(e -> {
            crearTriangulo();
        });

        JMenuItem EliminarItem = new JMenuItem("Eliminar");
        EliminarItem.addActionListener(e -> {
            eliminarFigura();
        });

        menuEmergente.add(cuadradoItem);
        menuEmergente.add(rectanguloItem);
        menuEmergente.add(circuloItem);
        menuEmergente.add(trianguloItem);
        menuEmergente.add(textItem);
        menuEmergente.add(EliminarItem);

        JMenuItem caerItem = new JMenuItem("Caer");

        caerItem.addActionListener(e -> {
            caer();
        });
        menuEmergente.add(caerItem);

        JMenuItem MoverItem = new JMenuItem("Mover");
        MoverItem.addActionListener(e -> {
            mover();
        });
        menuEmergente.add(MoverItem);

        JMenuItem GuardarItem = new JMenuItem("Guardar Panel");
        GuardarItem.addActionListener(e -> {
            guardar();
        });
        menuEmergente.add(GuardarItem);

        JMenuItem arcoIrisItem = new JMenuItem("ArcoIris");

        arcoIrisItem.addActionListener(e -> {
            cambioColorArcoiris();
        });

        menuEmergente.add(arcoIrisItem);

        JMenuItem cartaItem = new JMenuItem("Carta Blanca");

        cartaItem.addActionListener(e -> {
            crearCarta();
        });

        menuEmergente.add(cartaItem);

        JMenuItem cartaNegraItem = new JMenuItem("Carta Negra");

        cartaNegraItem.addActionListener(e -> {
            crearCartaNegra();
        });

        menuEmergente.add(cartaNegraItem);

        JMenuItem cartaDadaVueltaItem = new JMenuItem("Carta");

        cartaDadaVueltaItem.addActionListener(e -> {
            crearCartaDadaVuelta();
        });

        menuEmergente.add(cartaDadaVueltaItem);

        JMenuItem monedaItem = new JMenuItem("moneda");

        monedaItem.addActionListener(e -> {
            crearMoneda();
        });

        JMenuItem secItem = new JMenuItem("Secuecia");
        secItem.addActionListener(e -> {
            animacionSecuencial();
        });

        menuEmergente.add(secItem);

        menuEmergente.add(monedaItem);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                // Buscamos la figura que contiene las coordenadas del click
                for (Figura figura : figuras) {
                    if (figura.contiene(e.getX(), e.getY())) {
                        figuraSeleccionada = figura;
                        posicionRaton = e.getPoint();
                        break;
                    }
                }
                actualizarDatosFiguraSeleccionada();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e)) {
                    menuEmergente.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                if (figuraSeleccionada != null) {
                    int deltaX = e.getX() - posicionRaton.x;
                    int deltaY = e.getY() - posicionRaton.y;
                    int nuevaX = figuraSeleccionada.getX() + deltaX;
                    int nuevaY = figuraSeleccionada.getY() + deltaY;
                    figuraSeleccionada.setX(nuevaX);
                    figuraSeleccionada.setY(nuevaY);
                    posicionRaton = e.getPoint();
                    repaint();
                }
                actualizarDatosFiguraSeleccionada();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Figura figura : figuras) {
            figura.dibujar(g);
        }
    }

    public void actualizarDatosFiguraSeleccionada() {
        if (figuraSeleccionada != null) {
            // Actualizar etiquetas con los datos de la figura seleccionada
            tipoFiguraLabel.setText("Tipo de figura: " + figuraSeleccionada.getTipo());
            posicionXLabel.setText("Posicion X: " + figuraSeleccionada.getX());
            posicionYLabel.setText("Posicion Y: " + figuraSeleccionada.getY());

            // colorLabel.setText("Color :" +figuraSeleccionada.setColor());
        } else {
            // Si no hay figura seleccionada, limpiar las etiquetas
            tipoFiguraLabel.setText("Tipo de figura:");
            posicionXLabel.setText("Posicion X:");
            posicionYLabel.setText("Posicion Y:");
        }
    }

    public void guardar() {
        ArrayList<Figura> figurasGuardadas = new ArrayList<>();
        for (Figura figura : figuras) {
            figurasGuardadas.add(figura.clone());
        }
        figurasSec.add(figurasGuardadas);
    }

    public void crearTexto() {
        String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
        String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
        String inputa = JOptionPane.showInputDialog(this, "Ingrese texto");
        String inputTam = JOptionPane.showInputDialog(this, "Ingrese tamano:");
        int tam = Integer.parseInt(inputTam);
        int x = Integer.parseInt(inputx);
        int y = Integer.parseInt(inputy);

        // Pedir color de la figura
        colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.BLACK);

        // Crear objeto cuadrado con las dimensiones especificadas
        Letra cuad = new Letra(x, y, tam, inputa);
        cuad.setColor(colorSeleccionado);
        figuras.add(cuad);
        repaint();
    }

    public void crearCirculo() {
        escribirArchivo("circulo ");

        if (usoPorVoz) {
            int radius = Integer.parseInt("60");
            int x = Integer.parseInt("50");
            int y = Integer.parseInt("50");

            // Crear objeto Circulo con el radio especificado
            Circulo circ = new Circulo(x, y, colorSeleccionado);
            circ.setColor(Color.blue);
            circ.setRadio(radius);
            figuras.add(circ);
            repaint();
        } else {
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
            String inputRadius = JOptionPane.showInputDialog(this, "Ingrese el radio del circulo:");
            int radius = Integer.parseInt(inputRadius);
            int x = Integer.parseInt(inputx);
            int y = Integer.parseInt(inputy);

            // Pedir color de la figura
            colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.BLACK);

            // Crear objeto Circulo con el radio especificado
            Circulo circ = new Circulo(x, y, colorSeleccionado);
            circ.setColor(colorSeleccionado);
            circ.setRadio(radius);
            figuras.add(circ);
            repaint();
        }

    }

    public void crearCuadrado() {

        if (usoPorVoz) {
            int width = Integer.parseInt("50");
            int x = Integer.parseInt(lugarDeApraricion);
            int y = Integer.parseInt(lugarDeApraricion);

            // Crear objeto Rectangulo con las dimensiones especificadas
            Cuadrado rect = new Cuadrado(x, y, width);
            rect.setColor(Color.MAGENTA);
            rect.setAncho(width);
            figuras.add(rect);
            repaint();
        } else {
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
            String inputWidth = JOptionPane.showInputDialog(this, "Ingrese el lado del Cuadrado:");
            int width = Integer.parseInt(inputWidth);
            int x = Integer.parseInt(inputx);
            int y = Integer.parseInt(inputy);

            // Pedir color de la figura
            colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.BLACK);

            // Crear objeto Rectangulo con las dimensiones especificadas
            Cuadrado rect = new Cuadrado(x, y, width);
            rect.setColor(colorSeleccionado);
            rect.setAncho(width);
            figuras.add(rect);
            repaint();

        }
    }

    public void crearRectangulo() {
        escribirArchivo("rectangulo ");

        if (usoPorVoz) {
            int width = Integer.parseInt("50");
            int height = Integer.parseInt("100");
            int x = Integer.parseInt(lugarDeApraricion);
            int y = Integer.parseInt(lugarDeApraricion);

            // Crear objeto Rectangulo con las dimensiones especificadas
            Rectangulo rect = new Rectangulo(x, y, width, height);
            rect.setColor(Color.CYAN);
            rect.setAncho(width);
            rect.setAlto(height);
            figuras.add(rect);
            repaint();
        } else {

            // Pedir dimensiones del rectangulo
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
            String inputWidth = JOptionPane.showInputDialog(this, "Ingrese el ancho del rectangulo:");
            String inputHeight = JOptionPane.showInputDialog(this, "Ingrese el alto del rectangulo:");
            int width = Integer.parseInt(inputWidth);
            int height = Integer.parseInt(inputHeight);
            int x = Integer.parseInt(inputx);
            int y = Integer.parseInt(inputy);

            // Pedir color de la figura
            colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.BLACK);

            // Crear objeto Rectangulo con las dimensiones especificadas
            Rectangulo rect = new Rectangulo(x, y, width, height);
            rect.setColor(colorSeleccionado);
            rect.setAncho(width);
            rect.setAlto(height);
            figuras.add(rect);
            repaint();
        }

    }

    public void crearTriangulo() {

        if (usoPorVoz) {
            int base = Integer.parseInt("50");
            int height = Integer.parseInt("50");
            int x = Integer.parseInt(lugarDeApraricion);
            int y = Integer.parseInt(lugarDeApraricion);

            // Crear objeto Triangulo con la base y altura especificadas
            Triangulo triangulo = new Triangulo(x, y, base, height);
            triangulo.setColor(Color.GRAY);
            triangulo.setBase(base);
            triangulo.setAltura(height);
            figuras.add(triangulo);
            repaint();
        } else {
            // Pedir dimensiones del triangulo
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
            String inputBase = JOptionPane.showInputDialog(this, "Ingrese la base del triangulo:");
            String inputHeight = JOptionPane.showInputDialog(this, "Ingrese la altura del triangulo:");
            int base = Integer.parseInt(inputBase);
            int height = Integer.parseInt(inputHeight);
            int x = Integer.parseInt(inputx);
            int y = Integer.parseInt(inputy);

            // Pedir color de la figura
            colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.BLACK);

            // Crear objeto Triangulo con la base y altura especificadas
            Triangulo triangulo = new Triangulo(x, y, base, height);
            triangulo.setColor(colorSeleccionado);
            triangulo.setBase(base);
            triangulo.setAltura(height);
            figuras.add(triangulo);
            repaint();
        }

    }

    public void crearCarta() {
        escribirArchivo("cartaB ");

        if (usoPorVoz) {

            int x = Integer.parseInt(lugarDeApraricion);
            int y = Integer.parseInt(lugarDeApraricion);
            Carta carta = new Carta(x, y);
            carta.setColor(Color.BLACK);

            // Establecer el fondo de la parte trasera de la carta en negro
            carta.setColorFondo(Color.white);

            figuras.add(carta);
            Graphics g = getGraphics();
            carta.dibujar(g);
            repaint();
        } else {
            // Obtener el color de fondo seleccionado
            colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.BLACK);
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posición x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posición y:");
            int x = Integer.parseInt(inputx);
            int y = Integer.parseInt(inputy);
            Carta carta = new Carta(x, y);
            carta.setColor(colorSeleccionado);

            // Establecer el fondo de la parte trasera de la carta en negro
            carta.setColorFondo(Color.white);

            figuras.add(carta);
            Graphics g = getGraphics();
            carta.dibujar(g);
            repaint();
        }
    }

    public void crearCartaNegra() {
        escribirArchivo("cartaN ");
        if (usoPorVoz) {
            int x = Integer.parseInt(lugarDeApraricion);
            int y = Integer.parseInt(lugarDeApraricion);
            CartaNegra carta = new CartaNegra(x, y);
            carta.setColor(Color.white);

            // Establecer el fondo de la parte trasera de la carta en negro
            carta.setColorFondo(Color.black);

            figuras.add(carta);
            Graphics g = getGraphics();
            carta.dibujar(g);
            repaint();
        } else {
            // Obtener el color de fondo seleccionado
            colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.black);
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
            int x = Integer.parseInt(inputx);
            int y = Integer.parseInt(inputy);
            Carta carta = new Carta(x, y);
            carta.setColor(colorSeleccionado);

            // Establecer el fondo de la parte trasera de la carta en negro
            carta.setColorFondo(Color.black);

            figuras.add(carta);
            Graphics g = getGraphics();
            carta.dibujar(g);
            repaint();
        }
    }

    public void crearCartaDadaVuelta() {
        escribirArchivo("carta ");

        if (usoPorVoz) {
            int x = Integer.parseInt(lugarDeApraricion);
            int y = Integer.parseInt(lugarDeApraricion);
            CartaDadaVuelta cartaDadaVuelta = new CartaDadaVuelta(x, y);
            cartaDadaVuelta.setColor(colorSeleccionado);
            figuras.add(cartaDadaVuelta);
            Graphics g = getGraphics();
            cartaDadaVuelta.dibujar(g);
            repaint();
        } else {
            colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.BLACK);
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
            int x = Integer.parseInt(inputx);
            int y = Integer.parseInt(inputy);
            CartaDadaVuelta cartaDadaVuelta = new CartaDadaVuelta(x, y);
            cartaDadaVuelta.setColor(colorSeleccionado);
            figuras.add(cartaDadaVuelta);
            Graphics g = getGraphics();
            cartaDadaVuelta.dibujar(g);
            repaint();
        }
    }

    public void crearMoneda() {

        escribirArchivo("moneda ");
        if (usoPorVoz) {
            int x = Integer.parseInt(lugarDeApraricion);
            int y = Integer.parseInt(lugarDeApraricion);
            Moneda moneda = new Moneda(x, y);
            moneda.setColor(Color.BLACK);
            figuras.add(moneda);
            Graphics g = getGraphics();
            moneda.dibujar(g);
            repaint();
        } else {
            colorSeleccionado = JColorChooser.showDialog(this, "Seleccione un color", Color.BLACK);
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
            int x = Integer.parseInt(inputx);
            int y = Integer.parseInt(inputy);
            Moneda moneda = new Moneda(x, y);
            moneda.setColor(colorSeleccionado);
            figuras.add(moneda);
            Graphics g = getGraphics();
            moneda.dibujar(g);
            repaint();
        }
    }

    public void eliminarFigura() {
        escribirArchivo("eliminar ");

        if (figuraSeleccionada != null) {
            figuras.remove(figuraSeleccionada);
            figuraSeleccionada = null;
            repaint();
            actualizarDatosFiguraSeleccionada();
        }
    }

    
    
    public void caer() {
        if (figuraSeleccionada != null) {
            // Obtener la altura del panel
            int panelHeight = 650;// getHeight();

            // Iniciar animaci�n de ca�da para la figura seleccionada
            new Thread(() -> {
                int velocidadY = 5;
                while (figuraSeleccionada.getY() < panelHeight - figuraSeleccionada.getY()) {
                    figuraSeleccionada.setY(figuraSeleccionada.getY() + velocidadY);
                    repaint();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }

                // Iniciar animaci�n de rebote para la figura seleccionada
                int rebotes = 3;
                int velocidadRebote = 20;
                while (rebotes > 0) {
                    figuraSeleccionada.setY(figuraSeleccionada.getY() - velocidadRebote);
                    repaint();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    figuraSeleccionada.setY(figuraSeleccionada.getY() + velocidadRebote);
                    repaint();
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    rebotes--;
                }

                // Ajustar la posici�n final de la figura en caso de que se haya pasado
                if (figuraSeleccionada.getY() > panelHeight - figuraSeleccionada.getY()) {
                    figuraSeleccionada.setY(panelHeight - figuraSeleccionada.getY());
                    repaint();
                }
            }).start();
        }
    }

    
    
   
   
   
   

    public void mover() {
        if (figuraSeleccionada != null) {
            String inputx = JOptionPane.showInputDialog(this, "Ingrese la posicion x:");
            String inputy = JOptionPane.showInputDialog(this, "Ingrese la posicion y:");
            int nuevaX = Integer.parseInt(inputx);
            int nuevaY = Integer.parseInt(inputy);
            int duracionAnimacion = 1000;
            if (Temporizador != null && Temporizador.isRunning()) {
                Temporizador.stop();
            }
            // Calcular la cantidad de actualizaciones necesarias para la animaci�n
            int numActualizaciones = duracionAnimacion / 50; // actualizaciones cada 50 milisegundos
            // Calcular los incrementos en las coordenadas x e y para cada actualizaci�n
            int deltaX = (nuevaX - figuraSeleccionada.getX()) / numActualizaciones;
            int deltaY = (nuevaY - figuraSeleccionada.getY()) / numActualizaciones;
            // Crear un temporizador que actualice las coordenadas de la figura cada 50
            // milisegundos
            Temporizador = new Timer(50, new ActionListener() {
                int actualizacionActual = 0;

                @Override
                public void actionPerformed(ActionEvent e) {
                    // Actualizar las coordenadas de la figura con los incrementos correspondientes
                    int x = figuraSeleccionada.getX();
                    int y = figuraSeleccionada.getY();
                    x += deltaX;
                    y += deltaY;
                    figuraSeleccionada.setX(x);
                    figuraSeleccionada.setY(y);
                    actualizacionActual++;
                    // Si se han realizado todas las actualizaciones necesarias, detener el
                    // temporizador
                    if (actualizacionActual == numActualizaciones) {
                        Temporizador.stop();
                    }
                    // Repintar el panel para mostrar la figura en su nueva posici�n
                    repaint();
                }
            });
            Temporizador.start();
            actualizarDatosFiguraSeleccionada();
        }
    }

    public void cambioColorArcoiris() {
        if (figuraSeleccionada != null) {
            int duracionAnimacion = 5000; // Duraci�n de la animaci�n en milisegundos
            if (Temporizador != null && Temporizador.isRunning()) {
                Temporizador.stop();
            }
            // Calcular la cantidad de actualizaciones necesarias para la animaci�n
            int numActualizaciones = duracionAnimacion / 50; // actualizaciones cada 50 milisegundos
            // Calcular los incrementos en los componentes de color RGB para cada
            // actualizaci�n
            int incrementoR = 255 / numActualizaciones;
            int incrementoG = 0;
            int incrementoB = -255 / numActualizaciones;
            // Crear un temporizador que actualice los componentes de color RGB de la figura
            // cada 50 milisegundos
            Temporizador = new Timer(50, new ActionListener() {
                int actualizacionActual = 0;
                int r = 255, g = 0, b = 0; // Componentes de color RGB iniciales

                @Override
                public void actionPerformed(ActionEvent e) {
                    // Actualizar los componentes de color RGB de la figura con los incrementos
                    // correspondientes
                    r -= incrementoR;
                    g += incrementoG;
                    b -= incrementoB;
                    figuraSeleccionada.setColor(new Color(r, g, b));
                    actualizacionActual++;
                    // Si se han realizado todas las actualizaciones necesarias, detener el
                    // temporizador
                    if (actualizacionActual == numActualizaciones) {
                        Temporizador.stop();
                    }
                    // Repintar el panel para mostrar la figura con su nuevo color
                    repaint();
                }
            });
            Temporizador.start();
            actualizarDatosFiguraSeleccionada();
        }
    }

    
    
    
    public void animacionSecuencial() {
        figuras = new ArrayList<>(figurasSec.get(0)); // Configurar las figuras iniciales
        repaint();

        int duracionAnimacion = 2000; // Duración total de la animación en milisegundos
        int intervalo = duracionAnimacion / (figurasSec.size() - 1); // Intervalo de tiempo para cada transición
        ArrayList<ArrayList<Figura>> figurasSec1 = new ArrayList<>();
        for (ArrayList<Figura> conjunto : figurasSec) {
            ArrayList<Figura> copiaConjunto = new ArrayList<>();
            for (Figura figura : conjunto) {
                copiaConjunto.add(figura.clone()); // Suponiendo que Figura tiene un método "copiar()" para crear una
                                                   // copia independiente de la figura
            }
            figurasSec1.add(copiaConjunto);
        }

        Timer timer = new Timer(intervalo, new ActionListener() {
            int conjuntoActual = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                if (conjuntoActual < figurasSec.size() - 1) {
                    ArrayList<Figura> figurasInicio = new ArrayList<>(figurasSec.get(conjuntoActual));
                    ArrayList<Figura> figurasDestino = new ArrayList<>(figurasSec.get(conjuntoActual + 1));

                    // Iniciar la animación de transición
                    animarTransicion(figurasInicio, figurasDestino);

                    conjuntoActual++;
                } else {
                    figurasSec = new ArrayList<>(figurasSec1); // Restaurar el contenido original de figurasSec
                    ((Timer) e.getSource()).stop(); // Detener el temporizador cuando se han animado todas las
                                                    // transiciones
                }
            }
        });

        timer.start();
    }

    public void animarTransicion(ArrayList<Figura> figurasInicio, ArrayList<Figura> figurasDestino) {
        int duracionTransicion = 1000; // Duración de la transición en milisegundos
        int numActualizaciones = duracionTransicion / 50; // Número de actualizaciones para la transición

        for (int i = 0; i < figurasInicio.size(); i++) {
            Figura figuraInicio = figurasInicio.get(i);
            Figura figuraDestino = figurasDestino.get(i);

            int deltaX = (figuraDestino.getX() - figuraInicio.getX()) / numActualizaciones;
            int deltaY = (figuraDestino.getY() - figuraInicio.getY()) / numActualizaciones;

            moverFigura(figuraInicio, figuraDestino, deltaX, deltaY, numActualizaciones);
        }
    }

    
    
    
   



    public void moverFigura(Figura figuraInicio, Figura figuraDestino, int deltaX, int deltaY, int numActualizaciones) {
        Timer temporizador = new Timer(50, new ActionListener() {
            int actualizacionActual = 0;
            int xInicial = figuraInicio.getX();
            int yInicial = figuraInicio.getY();

            @Override
            public void actionPerformed(ActionEvent e) {
                int x = xInicial + (deltaX * actualizacionActual);
                int y = yInicial + (deltaY * actualizacionActual);

                figuraInicio.setX(x);
                figuraInicio.setY(y);

                actualizacionActual++;

                if (actualizacionActual == numActualizaciones) {
                    figuraInicio.setX(figuraDestino.getX());
                    figuraInicio.setY(figuraDestino.getY());

                    ((Timer) e.getSource()).stop();

                    // Actualizar las figuras para el próximo conjunto
                    figuras.remove(figuraInicio);
                    figuras.add(figuraDestino);

                    repaint();
                } else {
                    repaint();
                }
            }
        });

        temporizador.start();
    }

    public static void escribirArchivo(String contenido) {
        String nombreArchivo = "Estadisticas.txt";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true));
            writer.write(contenido);
            writer.close();
            System.out.println("Archivo escrito correctamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir el archivo: " + e.getMessage());
        }
    }
    
    
    
    public void guardarProyecto(String nombreArchivo) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(nombreArchivo));
            outputStream.writeObject(figuras);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void abrirProyecto(String nombreArchivo) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(nombreArchivo));
            figuras = (ArrayList<Figura>) inputStream.readObject();
            inputStream.close();
            repaint(); // Vuelve a dibujar el panel para mostrar las figuras cargadas
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void limpiarFiguras() {
        figuras.clear();
        repaint();
    }
    public void cargar(int indice) {
    if (indice >= 0 && indice < figurasSec.size()) {
        ArrayList<Figura> figurasGuardadas = figurasSec.get(indice);
        figuras.clear();
        figuras.addAll(figurasGuardadas);
        repaint();
    }
    }   
}
