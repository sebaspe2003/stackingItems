package tower;

import shapes.Rectangle;

/**
 * Taza normal que puede apilarse en la torre.
 * Compuesta visualmente por una base y dos paredes laterales.
 * Es la taza base del sistema; los demás tipos la extienden.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class Cup {

    private int number;
    private Rectangle base;
    private Rectangle leftWall;
    private Rectangle rightWall;
    private boolean hasLid;
    private Lid lid;
    private int xPos;
    private int yPos;
    private int cupWidth;
    private int cupHeight;

    /**
     * Crea una taza con el número identificador dado.
     * @param number número identificador de la taza
     */
    public Cup(int number) {
        this.number = number;
        this.hasLid = false;
        this.cupHeight = 30;
        this.cupWidth = 40;
        this.xPos = 70;
        this.yPos = 15;
        this.base = new Rectangle();
        this.leftWall = new Rectangle();
        leftWall.moveVertical(-cupHeight);
        this.rightWall = new Rectangle();
        rightWall.moveHorizontal(cupWidth - 10);
        rightWall.moveVertical(-cupHeight);
    }

    /**
     * Retorna el tipo de taza como cadena. Subclases deben sobreescribir.
     * @return "normal"
     */
    public String getType() {
        return "normal";
    }

    /**
     * Establece la posición gráfica de la taza.
     * @param x posición horizontal
     * @param y posición vertical (base de la taza)
     */
    public void setPosition(int x, int y) {
        int dx = x - xPos;
        int dy = y - yPos;
        xPos = x;
        yPos = y;
        base.moveHorizontal(dx);
        base.moveVertical(dy);
        leftWall.moveHorizontal(dx);
        leftWall.moveVertical(dy);
        rightWall.moveHorizontal(dx);
        rightWall.moveVertical(dy);
    }

    /**
     * Establece el color de la taza.
     * @param color nombre del color
     */
    public void setColor(String color) {
        base.changeColor(color);
        leftWall.changeColor(color);
        rightWall.changeColor(color);
    }

    /**
     * Cambia el tamaño de la taza.
     * @param height nueva altura de las paredes
     * @param width  nuevo ancho total
     */
    public void setSize(int height, int width) {
        int oldHeight = this.cupHeight;
        int oldWidth = this.cupWidth;
        this.cupWidth = width;
        this.cupHeight = height;
        base.changeSize(10, width);
        leftWall.changeSize(height, 10);
        rightWall.changeSize(height, 10);
        leftWall.moveVertical(oldHeight - height);
        rightWall.moveHorizontal(width - oldWidth);
        rightWall.moveVertical(oldHeight - height);
    }

    /** @return número identificador de la taza */
    public int getNumber() { return number; }

    /** @return true si la taza tiene tapa */
    public boolean hasLid() { return hasLid; }

    /**
     * Asocia una tapa a esta taza.
     * @param lid tapa a asociar
     */
    public void putLid(Lid lid) {
        this.lid = lid;
        this.hasLid = true;
    }

    /**
     * Desasocia y retorna la tapa de esta taza.
     * @return la tapa removida
     */
    public Lid removeLid() {
        Lid temp = this.lid;
        this.lid = null;
        this.hasLid = false;
        return temp;
    }

    /** @return la tapa asociada, o null si no tiene */
    public Lid getLid() { return lid; }

    /** Hace visible la taza en el canvas. */
    public void makeVisible() {
        base.makeVisible();
        leftWall.makeVisible();
        rightWall.makeVisible();
    }

    /** Hace invisible la taza en el canvas. */
    public void makeInvisible() {
        base.makeInvisible();
        leftWall.makeInvisible();
        rightWall.makeInvisible();
    }

    /** @return posición Y actual (base de la taza) */
    public int getYPos() { return yPos; }

    /** @return posición X actual */
    public int getXPos() { return xPos; }

    /** @return ancho actual de la taza */
    public int getWidth() { return cupWidth; }

    /** @return altura total de la taza (paredes + base) */
    public int getHeight() { return cupHeight + 10; }
}
