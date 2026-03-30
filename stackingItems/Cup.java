/**
 * Representa una taza que puede apilarse dentro de la torre.
 * 
 * Cada taza:
 * - Tiene un número identificador.
 * - Posee una representación gráfica compuesta por rectángulos.
 * - Puede estar asociada a una tapa.
 * - Puede existir sola o cubierta.
 * 
 * Cuando una taza está cubierta por su tapa, ambas deben
 * comportarse como una sola unidad dentro de la torre.
 * 
 * Esta clase reutiliza componentes gráficos del proyecto shapes.
 * 
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version 2.0
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
     * Constructor de la taza.
     * Inicializa la estructura visual y el número identificador.
     * 
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
     * Establece la posición gráfica de la taza.
     * 
     * @param x posición horizontal
     * @param y posición vertical
     */
    public void setPosition(int x, int y) {
        int dx = x - xPos;
        int dy = y - yPos;
        this.xPos = x;
        this.yPos = y;
        
        base.moveHorizontal(dx);
        base.moveVertical(dy);
        
        leftWall.moveHorizontal(dx);
        leftWall.moveVertical(dy);
        
        rightWall.moveHorizontal(dx);
        rightWall.moveVertical(dy);
    }
    
    public void setColor(String color) {
        base.changeColor(color);
        leftWall.changeColor(color);
        rightWall.changeColor(color);
    }
    
    public void setSize(int height, int width) {
        int oldHeight = this.cupHeight;
        int oldWidth = this.cupWidth;
        this.cupWidth = width;
        this.cupHeight = height;
        base.changeSize(10, width);
        leftWall.changeSize(height, 10);
        rightWall.changeSize(height, 10);
        // Ajustar posición relativa de paredes al nuevo tamaño
        leftWall.moveVertical(oldHeight - height);
        rightWall.moveHorizontal(width - oldWidth);
        rightWall.moveVertical(oldHeight - height);
    }
    
    /**
     * Retorna el número identificador de la taza.
     * 
     * @return número de la taza
     */
    public int getNumber() {
        return number;
    }
    
     /**
     * Indica si la taza tiene tapa asociada.
     * 
     * @return true si tiene tapa, false en caso contrario
     */
    public boolean hasLid() {
        return hasLid;
    }
    
    public void putLid(Lid lid) {
        this.lid = lid;
        this.hasLid = true;
    }
    
    public Lid removeLid() {
        Lid temp = this.lid;
        this.lid = null;
        this.hasLid = false;
        return temp;
    }
    
    public Lid getLid() {
        return lid;
    }
    
    public void makeVisible() {
        base.makeVisible();
        leftWall.makeVisible();
        rightWall.makeVisible();
    }
    
    public void makeInvisible() {
        base.makeInvisible();
        leftWall.makeInvisible();
        rightWall.makeInvisible();
    }
    
    public int getYPos() {
        return yPos;
    }
    
    public int getXPos() {
        return xPos;
    }
    
    public int getWidth() {
        return cupWidth;
    }

    public int getHeight() {
        return cupHeight + 10;
    }
}
