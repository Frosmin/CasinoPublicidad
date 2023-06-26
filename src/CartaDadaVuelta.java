// package src;



import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.io.Serializable;

public class CartaDadaVuelta extends Figura implements Serializable{
    private Color colorFondo;
    private int x;
    private int y;
    private final int ANCHO = 100;
    private final int ALTO = 150;

    // Constructor
    public CartaDadaVuelta(int x, int y) {
        this.x = x;
        this.y = y;
        this.colorFondo = Color.BLUE; // Color de fondo de la parte trasera de la carta
    }

    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Configurar antialiasing para bordes más suaves
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el fondo de la parte trasera de la carta
        g2d.setColor(colorFondo);
        g2d.fillRoundRect(x, y, ANCHO, ALTO, 10, 10);

        // Dibujar el borde de la parte trasera de la carta
        g2d.setColor(Color.BLACK);
        g2d.drawRoundRect(x, y, ANCHO, ALTO, 10, 10);

        // Dibujar un gráfico más bonito en la parte trasera de la carta
        g2d.setColor(Color.WHITE);

        Shape flower = createEvenMoreComplexFlowerShape(x + ANCHO / 2, y + ALTO / 2, 60);
        g2d.fill(flower);
    }

    private Shape createEvenMoreComplexFlowerShape(double centerX, double centerY, double size) {
        double radius = size / 2;
        double petalSize = radius / 3;

        GeneralPath path = new GeneralPath();

        for (int i = 0; i < 24; i++) {
            double angle = 2 * Math.PI * (i / 24.0);
            double x = centerX + radius * Math.cos(angle);
            double y = centerY + radius * Math.sin(angle);
            path.moveTo(x, y);

            for (int j = 1; j < 6; j++) {
                double petalAngle = angle + j * (2 * Math.PI / 6.0);
                double petalX = x + petalSize * Math.cos(petalAngle);
                double petalY = y + petalSize * Math.sin(petalAngle);
                path.quadTo(x, y, petalX, petalY);
            }

            path.closePath();
        }

        return path;
    }

    public void setColorFondo(Color colorFondo) {
        this.colorFondo = colorFondo;
    }

    public String getTipo() {
        return "Carta de Póker dada vuelta (Parte trasera)";
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

}
