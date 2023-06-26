// package src;






//package src;


import java.awt.Graphics;
import java.awt.Color;
import java.io.Serializable;

public class Circulo extends Figura implements Serializable{
    private int x, y, radio;
    private Color color;
    // Constructor
    public Circulo(int x, int y, Color c) {
         this.x = x;
         this.y = y;
         this.color = Color.black;
        //super(x,y,"Circulo");
    }
    // Métodos para obtener y establecer los valores de los atributos
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
    public int getRadio() {
        return radio;
    }
    public void setRadio(int radio) {
        this.radio = radio;
    }
    public void setColor(Color c){
        color = c;
    }
    public boolean contiene(int x, int y) {
    return Math.pow(getX() - x, 2) + Math.pow(getY() - y, 2) <= Math.pow(getRadio(), 2);
    }
    // Método para dibujar el círculo
    public void dibujar(Graphics g) {
        g.setColor(color);
        g.fillOval(x - radio, y - radio, radio*2, radio*2);
    }
    
    public String getTipo(){
        return "Circulo";
    }
    @Override
    public Circulo clone() {
        return (Circulo) super.clone();
     }
}