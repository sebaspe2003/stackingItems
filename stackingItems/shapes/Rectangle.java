package shapes;

/**
 * Rectángulo que puede manipularse y dibujarse en el canvas.
 * Extiende Shape heredando visibilidad, color y movimiento.
 *
 * @author Michael Kolling and David J. Barnes (Modified)
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class Rectangle extends Shape {

    public static final int EDGES = 4;

    private int height;
    private int width;

    /**
     * Crea un rectángulo con posición y color por defecto.
     */
    public Rectangle() {
        height = 30;
        width = 40;
        xPosition = 70;
        yPosition = 15;
        color = "magenta";
        isVisible = false;
    }

    /**
     * Cambia el tamaño del rectángulo.
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
            canvas.draw(this, color,
                new java.awt.Rectangle(xPosition, yPosition, width, height));
            canvas.wait(10);
        }
    }
}
