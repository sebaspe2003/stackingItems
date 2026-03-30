package shapes;

import java.awt.Graphics2D;

/**
 * Clase abstracta base para todas las figuras del paquete shapes.
 * Define el contrato común de visibilidad, color y movimiento.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public abstract class Shape {

    protected int xPosition;
    protected int yPosition;
    protected String color;
    protected boolean isVisible;

    /**
     * Hace visible la figura. Si ya era visible, no hace nada.
     */
    public void makeVisible() {
        isVisible = true;
        draw();
    }

    /**
     * Hace invisible la figura. Si ya era invisible, no hace nada.
     */
    public void makeInvisible() {
        erase();
        isVisible = false;
    }

    /**
     * Mueve la figura horizontalmente.
     * @param distance distancia en píxeles (negativo = izquierda)
     */
    public void moveHorizontal(int distance) {
        erase();
        xPosition += distance;
        draw();
    }

    /**
     * Mueve la figura verticalmente.
     * @param distance distancia en píxeles (negativo = arriba)
     */
    public void moveVertical(int distance) {
        erase();
        yPosition += distance;
        draw();
    }

    /**
     * Cambia el color de la figura.
     * @param newColor nuevo color ("red", "blue", "green", "yellow", "magenta", "black", "white")
     */
    public void changeColor(String newColor) {
        color = newColor;
        draw();
    }

    /**
     * Dibuja la figura en el canvas si es visible.
     */
    protected abstract void draw();

    /**
     * Borra la figura del canvas si era visible.
     */
    protected void erase() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
