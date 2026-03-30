package shapes;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canvas permite dibujar figuras gráficas en una ventana.
 * Implementado como singleton. Modificado para el paquete shapes.
 *
 * @author Bruce Quig
 * @author Michael Kolling (mik)
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class Canvas {

    private static final Logger LOGGER = Logger.getLogger(Canvas.class.getName());
    private static Canvas canvasSingleton;

    /**
     * Retorna la instancia única del canvas (patrón singleton).
     * @return instancia del canvas
     */
    public static Canvas getCanvas() {
        if (canvasSingleton == null) {
            canvasSingleton = new Canvas("Tower Simulator", 300, 300, Color.white);
        }
        canvasSingleton.setVisible(true);
        return canvasSingleton;
    }

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private List<Object> objects;
    private HashMap<Object, ShapeDescription> shapes;

    private Canvas(String title, int width, int height, Color bgColour) {
        frame = new JFrame();
        canvas = new CanvasPane();
        frame.setContentPane(canvas);
        frame.setTitle(title);
        canvas.setPreferredSize(new Dimension(width, height));
        backgroundColour = bgColour;
        frame.pack();
        objects = new ArrayList<>();
        shapes = new HashMap<>();
    }

    /**
     * Muestra u oculta el canvas.
     * @param visible true para mostrar, false para ocultar
     */
    public void setVisible(boolean visible) {
        if (graphic == null) {
            Dimension size = canvas.getSize();
            canvasImage = canvas.createImage(size.width, size.height);
            graphic = (Graphics2D) canvasImage.getGraphics();
            graphic.setColor(backgroundColour);
            graphic.fillRect(0, 0, size.width, size.height);
            graphic.setColor(Color.black);
        }
        frame.setVisible(visible);
    }

    /**
     * Dibuja una figura en el canvas.
     * @param referenceObject objeto de referencia para identidad
     * @param color           color de la figura
     * @param shape           figura a dibujar
     */
    public void draw(Object referenceObject, String color, java.awt.Shape shape) {
        objects.remove(referenceObject);
        objects.add(referenceObject);
        shapes.put(referenceObject, new ShapeDescription(shape, color));
        redraw();
    }

    /**
     * Borra una figura del canvas.
     * @param referenceObject objeto a borrar
     */
    public void erase(Object referenceObject) {
        objects.remove(referenceObject);
        shapes.remove(referenceObject);
        redraw();
    }

    /**
     * Establece el color de primer plano del canvas.
     * @param colorString nombre del color
     */
    public void setForegroundColor(String colorString) {
        switch (colorString) {
            case "red":     graphic.setColor(Color.red);     break;
            case "black":   graphic.setColor(Color.black);   break;
            case "blue":    graphic.setColor(Color.blue);    break;
            case "yellow":  graphic.setColor(Color.yellow);  break;
            case "green":   graphic.setColor(Color.green);   break;
            case "magenta": graphic.setColor(Color.magenta); break;
            case "white":   graphic.setColor(Color.white);   break;
            case "orange":  graphic.setColor(Color.orange);  break;
            case "cyan":    graphic.setColor(Color.cyan);    break;
            case "gray":    graphic.setColor(Color.gray);    break;
            default:        graphic.setColor(Color.black);   break;
        }
    }

    /**
     * Espera un número de milisegundos (útil para animaciones).
     * @param milliseconds tiempo de espera
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Thread interrupted during wait", e);
            Thread.currentThread().interrupt();
        }
    }

    private void redraw() {
        eraseBackground();
        for (Iterator<Object> i = objects.iterator(); i.hasNext(); ) {
            shapes.get(i.next()).draw(graphic);
        }
        canvas.repaint();
    }

    private void eraseBackground() {
        Color original = graphic.getColor();
        graphic.setColor(backgroundColour);
        Dimension size = canvas.getSize();
        graphic.fill(new java.awt.Rectangle(0, 0, size.width, size.height));
        graphic.setColor(original);
    }

    private class CanvasPane extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(canvasImage, 0, 0, null);
        }
    }

    private class ShapeDescription {
        private java.awt.Shape shape;
        private String colorString;

        ShapeDescription(java.awt.Shape shape, String color) {
            this.shape = shape;
            this.colorString = color;
        }

        void draw(Graphics2D graphic) {
            setForegroundColor(colorString);
            graphic.fill(shape);
        }
    }
}
