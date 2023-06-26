// package src;




//package src;

import java.awt.Graphics;
import java.awt.Color;
import java.io.Serializable;

public class Rectangulo extends Figura implements Serializable{
    private int x, y, ancho, alto;
    private Color color;

    // Constructor
    public Rectangulo(int x, int y, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.color = Color.black;
        this.ancho = ancho;
        this.alto = alto;
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
        return ancho;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public int getAlto() {
        return alto;
    }

    public void setAlto(int alto) {
        this.alto = alto;
    }

    public void setColor(Color c) {
        this.color = c;
    }

    // Método para dibujar el rectángulo
    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, ancho, alto);
    }

    public boolean contiene(int x, int y) {
        return getX() <= x && x <= getX() + getAncho() && getY() <= y && y <= getY() + getAlto();
    }

    public String getTipo() {
        return "Rectangulo";
    }
    @Override
    public Rectangulo clone() {
        return (Rectangulo) super.clone();
     }
}