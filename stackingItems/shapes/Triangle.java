package shapes;

import java.awt.Polygon;

/**
 * Triángulo que puede manipularse y dibujarse en el canvas.
 * Extiende Shape heredando visibilidad, color y movimiento.
 *
 * @author Michael Kolling and David J. Barnes (Modified)
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class Triangle extends Shape {

    public static final int VERTICES = 3;

    private int height;
    private int width;

    /**
     * Crea un triángulo con posición y color por defecto.
     */
    public Triangle() {
        height = 30;
        width = 40;
        xPosition = 140;
        yPosition = 15;
        color = "green";
        isVisible = false;
    }

    /**
     * Cambia el tamaño del triángulo.
     * @param newHeight nueva altura en píxeles
     * @param newWidth  nuevo ancho en píxeles
     */
    public void changeSize(int newHeight, int newWidth) {
        erase();
        height = newHeight;
        width = newWidth;
        draw();
    }

    /** @return altura actual en píxeles */
    public int getHeight() { return height; }

    /** @return ancho actual en píxeles */
    public int getWidth() { return width; }

    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            int[] xpoints = { xPosition, xPosition + (width / 2), xPosition - (width / 2) };
            int[] ypoints = { yPosition, yPosition + height, yPosition + height };
            canvas.draw(this, color, new Polygon(xpoints, ypoints, 3));
            canvas.wait(10);
        }
    }
}
