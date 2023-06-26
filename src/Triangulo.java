// package src;



import java.awt.Graphics;
import java.awt.Color;
import java.io.Serializable;

public class Triangulo extends Figura implements Serializable{
    private int x;
    private int y, base, altura;
    private Color color;

    public Triangulo(int x, int y, int base, int altura) {
        this.x = x;
        this.y = y;
        this.color = Color.black;
        this.altura = altura;
        this.base = base;
    }

    public double getArea() {
        return (int) (0.5 * base * altura);
    }

    public void setBase(int base) {
        this.base = base;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void dibujar(Graphics g) {
        g.setColor(color);
        int[] xPoints = { x, x + (base / 2), x - (base / 2) };
        int[] yPoints = { y - altura, y, y };
        g.fillPolygon(xPoints, yPoints, 3);
    }

    public int getBase() {
        return base;
    }

    public int getAltura() {
        return altura;
    }

    public void setColor(Color c) {
        color = c;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public boolean contiene(int x, int y) {
        double area1 = areaTriangulo(x, y, getX(), getY(), getX() + getBase(), getY());
        double area2 = areaTriangulo(x, y, getX() + getBase(), getY(), getX() + (getBase() / 2), getY() - getAltura());
        double area3 = areaTriangulo(x, y, getX() + (getBase() / 2), getY() - getAltura(), getX(), getY());
        double areaTotal = getArea();

        return area1 + area2 + area3 >= areaTotal;
    }

    private double areaTriangulo(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }

    public String getTipo() {
        return "Triagulo";
    }
    @Override
    public Triangulo clone() {
        return (Triangulo) super.clone();
     }
}
