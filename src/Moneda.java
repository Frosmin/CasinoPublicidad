// package src;



import java.awt.Stroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.BasicStroke;
import java.io.Serializable;

public class Moneda extends Figura implements Serializable{
    private Color color1;
    private Color color2;
    private int x;
    private int y;
    private final int DIAMETRO = 100;

    // Constructor
    public Moneda(int x, int y) {
        this.x = x;
        this.y = y;
        setColor(Color.YELLOW); // Establecer el color de la moneda como amarillo
        setColor2(Color.ORANGE);
    }

    public void dibujar(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Configurar antialiasing para bordes más suaves
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el fondo de la moneda
        g2d.setColor(color1);
        g2d.fill(new Ellipse2D.Double(x, y, DIAMETRO, DIAMETRO));

        // Dibujar el segundo color del fondo de la moneda
        g2d.setColor(color2);
        g2d.fill(new Ellipse2D.Double(x + 10, y + 10, DIAMETRO - 20, DIAMETRO - 20));

        // Dibujar el borde de la moneda con colores intercalados
        Stroke borde = new BasicStroke(8); // Establecer el grosor del borde de la moneda
        Stroke bordeIntercalado = new BasicStroke(8, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 0, new float[] { 8 }, 0); // Configurar borde intercalado
        g2d.setStroke(bordeIntercalado);
        g2d.setColor(color1);
        g2d.draw(new Ellipse2D.Double(x, y, DIAMETRO, DIAMETRO));
        g2d.setStroke(borde);
        g2d.setColor(color2);
        g2d.draw(new Ellipse2D.Double(x, y, DIAMETRO, DIAMETRO));

        // Dibujar el símbolo de la moneda
        g2d.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 60);
        g2d.setFont(font);
        g2d.drawString("$", x + DIAMETRO / 2 - 20, y + DIAMETRO / 2 + 20);
    }

    public void setColor(Color c1) {
        color1 = c1;
    }
    public void setColor2(Color c1) {
        color2 = c1;
    }

    public String getTipo() {
        return "Moneda de Casino";
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

    public int getDiametro() {
        return DIAMETRO;
    }

    public boolean contiene(int x, int y) {
        double radio = DIAMETRO / 2.0;
        double centroX = this.x + radio;
        double centroY = this.y + radio;
        double distancia = Math.sqrt(Math.pow(x - centroX, 2) + Math.pow(y - centroY, 2));
        return distancia <= radio;
    }
    @Override
    public Moneda clone() {
        return (Moneda) super.clone();
     }
}
