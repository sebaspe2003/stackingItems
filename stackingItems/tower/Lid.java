package tower;

import shapes.Rectangle;

/**
 * Tapa normal que cubre a su taza asociada.
 * Es la tapa base del sistema; los demás tipos la extienden.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class Lid {

    private int cupNumber;
    private Rectangle visual;
    private int xPos;
    private int yPos;

    /**
     * Crea una tapa asociada al número de taza dado.
     * @param cupNumber número de la taza asociada
     */
    public Lid(int cupNumber) {
        this.cupNumber = cupNumber;
        this.visual = new Rectangle();
        this.xPos = 70;
        this.yPos = 15;
    }

    /**
     * Retorna el tipo de tapa como cadena. Subclases deben sobreescribir.
     * @return "normal"
     */
    public String getType() {
        return "normal";
    }

    /**
     * Establece la posición gráfica de la tapa.
     * @param x posición horizontal
     * @param y posición vertical
     */
    public void setPosition(int x, int y) {
        visual.moveHorizontal(x - xPos);
        visual.moveVertical(y - yPos);
        this.xPos = x;
        this.yPos = y;
    }

    /**
     * Establece el ancho visual de la tapa.
     * @param width ancho en píxeles
     */
    public void setSize(int width) {
        visual.changeSize(5, width);
    }

    /**
     * Establece el color de la tapa.
     * @param color nombre del color
     */
    public void setColor(String color) {
        visual.changeColor(color);
    }

    /** @return número de la taza asociada */
    public int getCupNumber() { return cupNumber; }

    /** @return el rectángulo visual de la tapa */
    public Rectangle getVisual() { return visual; }

    /** Hace visible la tapa en el canvas. */
    public void makeVisible() { visual.makeVisible(); }

    /** Hace invisible la tapa en el canvas. */
    public void makeInvisible() { visual.makeInvisible(); }
}
