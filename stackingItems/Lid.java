/**
 * Representa la tapa de una taza dentro de la torre.
 * 
 * Cada tapa:
 * - Está asociada a una taza mediante su número.
 * - Tiene una altura fija de 1 cm.
 * - Comparte características visuales con la taza.
 * 
 * Cuando la taza está cubierta, tapa y taza deben moverse juntas.
 * 
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 2.0
 */
public class Lid {
    private int cupNumber;
    private Rectangle visual;
    private int xPos;
    private int yPos;
    
    /**
     * Constructor de la tapa.
     * 
     * @param cupNumber número de la taza asociada
     */
    public Lid(int cupNumber) {
        this.cupNumber = cupNumber;
        this.visual = new Rectangle();
        this.xPos = 70;
        this.yPos = 15;
    }
    
    /**
     * Establece la posición gráfica de la tapa.
     * 
     * @param x posición horizontal
     * @param y posición vertical
     */
    public void setPosition(int x, int y) {
        visual.moveHorizontal(x - xPos);
        visual.moveVertical(y - yPos);
        this.xPos = x;
        this.yPos = y;
    }
    
    public void setSize(int width) {
        visual.changeSize(5, width);
    }
    
    public void setColor(String color) {
        visual.changeColor(color);
    }
    
    /**
     * Retorna el número de la taza asociada.
     * 
     * @return número de la taza
     */
    public int getCupNumber() {
        return cupNumber;
    }
    
    public Rectangle getVisual() {
        return visual;
    }
    
    public void makeVisible() {
        visual.makeVisible();
    }
    
    public void makeInvisible() {
        visual.makeInvisible();
    }
}
