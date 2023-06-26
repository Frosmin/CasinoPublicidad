// package src;


import javax.swing.*;

import manipulacionPaquete.Libreria;

import java.awt.*;
import java.util.ArrayList;

public class Estadisticas extends JPanel {
    private ArrayList<Integer> valores;
    private ArrayList<String> nombres;
    private Color[] colores = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.ORANGE, Color.CYAN,
            Color.MAGENTA };
    int carta, cartaB, cartaN, moneda, Rectangulo, Circulo, Eliminar;

    public int getCarta() {
        return carta;
    }

    public void setCarta(int carta) {
        this.carta = carta;
    }

    public int getCartaB() {
        return cartaB;
    }

    public void setCartaB(int cartaB) {
        this.cartaB = cartaB;
    }

    public int getCartaN() {
        return cartaN;
    }

    public void setCartaN(int cartaN) {
        this.cartaN = cartaN;
    }

    public int getMoneda() {
        return moneda;
    }

    public void setMoneda(int moneda) {
        this.moneda = moneda;
    }

    public int getRectangulo() {
        return Rectangulo;
    }

    public void setRectangulo(int rectangulo) {
        Rectangulo = rectangulo;
    }

    public int getCirculo() {
        return Circulo;
    }

    public void setCirculo(int circulo) {
        Circulo = circulo;
    }

    public int getEliminar() {
        return Eliminar;
    }

    public void setEliminar(int eliminar) {
        Eliminar = eliminar;
    }

    public Estadisticas() {
        Libreria ob = new Libreria();
        setCarta(ob.repeticiones(ob.leerArchivo("Estadisticas.txt"), "carta"));
        setCartaB(ob.repeticiones(ob.leerArchivo("Estadisticas.txt"), "cartaB"));
        setCartaN(ob.repeticiones(ob.leerArchivo("Estadisticas.txt"), "cartaN"));

        setMoneda(ob.repeticiones(ob.leerArchivo("Estadisticas.txt"), "moneda"));
        setRectangulo(ob.repeticiones(ob.leerArchivo("Estadisticas.txt"), "rectangulo"));
        setCirculo(ob.repeticiones(ob.leerArchivo("Estadisticas.txt"), "circulo"));
        setEliminar(ob.repeticiones(ob.leerArchivo("Estadisticas.txt"), "eliminar"));

        setLayout(new BorderLayout());
        valores = new ArrayList<>();
        // Agrega los valores de altura de las barras a la lista
        valores.add(getCarta());
        valores.add(getCartaN());
        valores.add(getCartaB());
        valores.add(getMoneda());
        valores.add(getRectangulo());
        valores.add(getCirculo());
        valores.add(getEliminar());

        nombres = new ArrayList<>();
        // Agrega los nombres correspondientes a cada rectángulo
        nombres.add("Carta"); // carta volcada
        nombres.add("Carta Negra");
        nombres.add("Carta Blanca");
        nombres.add("Moneda");
        nombres.add("Rectángulo");
        nombres.add("Circulo");
        nombres.add("Eliminar");

        JTable table = new JTable(
                new Object[][] { { "Carta", getCarta() }, { "Carta Negra", getCartaN() },
                        { "Carta Blanca", getCartaB() }, { "Moneda", getMoneda() }, { "Rectángulo", getRectangulo() },
                        { "Circulo", getCirculo() }, { "Eliminar", getEliminar() } },
                new Object[] { "Nombre", "Tamaño" });
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.NORTH);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int barWidth = getWidth() / valores.size();
        int maxHeight = getHeight() - 50; // Ajusta la altura para dejar espacio para la tabla

        for (int i = 0; i < valores.size(); i++) {
            int barHeight = valores.get(i) * 2; // Valor * 2 para que la barra sea visible
            int x = i * barWidth;
            int y = maxHeight - barHeight;

            g.setColor(colores[i % colores.length]); // Obtén un color distinto para cada rectángulo

            g.fillRect(x, y, barWidth, barHeight);

            g.setColor(Color.BLACK); // Establece el color del texto en negro
            String nombre = nombres.get(i); // Obtiene el nombre correspondiente al rectángulo
            int textX = x + barWidth / 2 - g.getFontMetrics().stringWidth(nombre) / 2;
            int textY = y - 5; // Ajusta la posición del texto sobre la barra
            g.drawString(nombre, textX, textY);
        }

        // Dibuja los rectángulos en la parte inferior
        int rectWidth = getWidth() / valores.size();
        int rectHeight = 20;
        int rectY = maxHeight + 10;
        for (int i = 0; i < valores.size(); i++) {
            int rectX = i * rectWidth;
            g.setColor(colores[i % colores.length]);
            g.fillRect(rectX, rectY, rectWidth, rectHeight);
            g.setColor(Color.BLACK);
            g.drawRect(rectX, rectY, rectWidth, rectHeight);
            String tamano = valores.get(i).toString();
            int textX = rectX + rectWidth / 2 - g.getFontMetrics().stringWidth(tamano) / 2;
            int textY = rectY + rectHeight + 15;
            g.drawString(tamano, textX, textY);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1000, 800); // Ajusta el tamaño preferido del panel
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Estadisticas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Estadisticas());
        frame.pack();
        frame.setVisible(true);
    }
}
