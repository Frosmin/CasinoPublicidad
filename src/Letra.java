// package src;




import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.io.Serializable;

public class Letra extends Figura implements Serializable{
    private int x, y, tam;
    String a;
    private Color color;

    // Constructor
    public Letra(int x, int y,int tam, String a) {
        this.x = x;
        this.y = y;
        this.tam = tam;
        this.color = Color.black;
        this.a = a;
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

   
    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }
    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }
    public void setColor(Color c) {
        this.color = c;
    }

    // Método para dibujar el rectángulo
    public void dibujar(Graphics g) {
        g.setColor(color);
        g.setFont(new Font("Arial", Font.BOLD, tam));
        g.drawString(a, x, y);    
    }

    public boolean contiene(int x, int y) {
    int letraX = getX();
    int letraY = getY();

    return x >= letraX && x <= letraX + tam && y >= letraY - tam && y <= letraY;
    }

    public String getTipo() {
        return "Letra";
    }
    @Override
    public Letra clone() {
        return (Letra) super.clone();
     }
}