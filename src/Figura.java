// package src;




import java.awt.Color;
import java.awt.Graphics;

public abstract class Figura implements Cloneable {
    private int x;
    private int y;
    private Color color;
    private String tipo;

    public abstract void dibujar(Graphics g);

    public boolean contiene(int x, int y) {
        return false;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    @Override
    public Figura clone() {
        try {
            return (Figura) super.clone();
        } catch (CloneNotSupportedException e) {
            // Manejar la excepción si ocurre
            return null;
        }
    }
}