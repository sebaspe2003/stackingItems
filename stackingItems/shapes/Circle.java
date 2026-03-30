package shapes;

import java.awt.geom.Ellipse2D;

/**
 * Círculo que puede manipularse y dibujarse en el canvas.
 * Extiende Shape heredando visibilidad, color y movimiento.
 *
 * @author Michael Kolling and David J. Barnes (Modified)
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class Circle extends Shape {

    public static final double PI = 3.1416;

    private int diameter;

    /**
     * Crea un círculo con posición y color por defecto.
     */
    public Circle() {
        diameter = 30;
        xPosition = 20;
        yPosition = 15;
        color = "blue";
        isVisible = false;
    }

    /**
     * Cambia el diámetro del círculo.
     * @param newDiameter nuevo diámetro en píxeles
     */
    public void changeSize(int newDiameter) {
        erase();
        diameter = newDiameter;
        draw();
    }

    /** @return diámetro actual en píxeles */
    public int getDiameter() { return diameter; }

    @Override
    protected void draw() {
        if (isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.draw(this, color,
                new Ellipse2D.Double(xPosition, yPosition, diameter, diameter));
            canvas.wait(10);
        }
    }
}
