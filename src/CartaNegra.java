// package src;




import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.io.Serializable;


public class CartaNegra extends Figura implements Serializable{
    private Color color1;
    private int x;
    private int y;
    private final int ANCHO = 100;
    private final int ALTO = 150;
    private Color colorFondo;

    // Constructor
    public CartaNegra(int x, int y) {
        this.x = x;
        this.y = y;
        this.colorFondo = Color.WHITE; // Color de fondo de la parte trasera de la carta
    }

    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Configurar antialiasing para bordes más suaves
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el fondo de la carta
        g2d.setColor(colorFondo);
        g2d.fillRoundRect(x, y, ANCHO, ALTO, 10, 10);

        // Dibujar los bordes de la carta
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x, y, ANCHO, ALTO, 10, 10);

        // Dibujar los símbolos y números de la carta
        g2d.setColor(color1);

        // Dibujar el número o letra de la carta
        Font font = new Font("Arial", Font.BOLD, 50);
        g2d.setFont(font);
        g2d.drawString("A", x + ANCHO / 2 - 20, y + ALTO / 2 + 20);

        // Dibujar el corazón
        Shape heart = createHeartShape(x + ANCHO / 2 - 25, y + ALTO - 130, 20);
        g2d.fill(heart);
    }

    public void setColor(Color c1) {
        color1 = c1;
    }

    public String getTipo() {
        return "Carta de Póker";
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getAncho() {
        return ANCHO;
    }

    public int getAlto() {
        return ALTO;
    }

    public boolean contiene(int x, int y) {
        return (getX() <= x && x <= getX() + getAncho() && getY() <= y && y <= getY() + getAlto());
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    // Método para crear una forma de corazón
    private Shape createHeartShape(double centerX, double centerY, double size) {
        double c = size / 2; // control point distance
        double x = centerX;
        double y = centerY - size / 6;

        GeneralPath path = new GeneralPath();
        path.moveTo(x, y);
        path.curveTo(x, y - c, x - c, y - c, x - c, y);
        path.curveTo(x - c, y + c, x, y + c, x, y + c * 2);
        path.curveTo(x, y + c, x + c, y + c, x + c, y);
        path.curveTo(x + c, y - c, x, y - c, x, y);

        return path;
    }
}
