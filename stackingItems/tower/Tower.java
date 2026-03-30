package tower;

import shapes.Rectangle;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Torre donde se apilan tazas con sus tapas.
 * Soporta tazas de tipo normal, opener, hierarchical y frozen.
 * Soporta tapas de tipo normal, fearful, crazy y sticky.
 *
 * Reglas especiales:
 * - OpenerCup: al entrar elimina todas las tapas sueltas que le impiden el paso.
 * - HierarchicalCup: al entrar desplaza hacia arriba todos los objetos de menor número;
 *   si queda en el fondo (índice 0), no puede ser removida.
 * - FrozenCup: no puede ser movida por swap mientras esté congelada.
 * - FearfulLid: no entra si su taza no está en la torre; no sale si está tapando a su taza.
 * - CrazyLid: al entrar se ubica en la base de la torre en lugar de tapar a su taza.
 * - StickyLid: al salir arrastra consigo la taza que estaba inmediatamente debajo.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class Tower {

    private int width;
    private int maxHeight;
    private ArrayList<Cup> cups;
    private ArrayList<Lid> lids;
    private ArrayList<Object> towerItems;
    private boolean isVisible;
    private Rectangle axisX;
    private ArrayList<Rectangle> axisY;

    /**
     * Crea una torre con ancho y altura máxima dados.
     * @param width     ancho de referencia para el canvas
     * @param maxHeight número máximo de tazas permitidas
     */
    public Tower(int width, int maxHeight) {
        this.width = width;
        this.maxHeight = maxHeight;
        this.cups = new ArrayList<>();
        this.lids = new ArrayList<>();
        this.towerItems = new ArrayList<>();
        this.isVisible = false;
        this.axisY = new ArrayList<>();
        createAxis();
    }

    /**
     * Crea una torre con n tazas normales apiladas de mayor a menor.
     * @param cupsCount número de tazas a crear
     */
    public Tower(int cupsCount) {
        this(9, 1000);
        for (int i = cupsCount; i >= 1; i--) {
            pushCup(i, (2 * i - 1) * 10);
        }
    }

    // -------------------------------------------------------------------------
    // Métodos de inserción
    // -------------------------------------------------------------------------

    /**
     * Inserta una taza normal en la torre aplicando las reglas de su tipo.
     * @param number número identificador de la taza
     * @param width  ancho visual de la taza
     */
    public void pushCup(int number, int width) {
        pushCupObject(new Cup(number), width);
    }

    /**
     * Inserta una taza opener en la torre.
     * Elimina todas las tapas sueltas presentes antes de insertarse.
     * @param number número identificador
     * @param width  ancho visual
     */
    public void pushOpenerCup(int number, int width) {
        removeAllLooseLids();
        pushCupObject(new OpenerCup(number), width);
    }

    /**
     * Inserta una taza hierarchical en la torre.
     * Desplaza hacia arriba todos los objetos de tazas con número menor.
     * Si queda en el fondo, se marca como inamovible.
     * @param number número identificador
     * @param width  ancho visual
     */
    public void pushHierarchicalCup(int number, int width) {
        HierarchicalCup cup = new HierarchicalCup(number);
        cup.setColor("cyan");
        cup.setSize(30, width);

        // Insertar al frente desplazando objetos de menor número
        int insertIndex = 0;
        for (int i = 0; i < towerItems.size(); i++) {
            Object obj = towerItems.get(i);
            if (obj instanceof Cup && ((Cup) obj).getNumber() < number) {
                insertIndex = i;
                break;
            }
            insertIndex = i + 1;
        }
        cups.add(cup);
        towerItems.add(insertIndex, cup);

        if (insertIndex == 0) {
            cup.setAtBottom(true);
        }
        if (isVisible) updatePositions();
    }

    /**
     * Inserta una taza frozen en la torre.
     * @param number número identificador
     * @param width  ancho visual
     */
    public void pushFrozenCup(int number, int width) {
        pushCupObject(new FrozenCup(number), width);
    }

    /**
     * Inserta una tapa normal suelta en la torre.
     * @param cupNumber número de la taza asociada
     */
    public void pushLid(int cupNumber) {
        Lid lid = new Lid(cupNumber);
        lid.setColor("black");
        lids.add(lid);
        towerItems.add(lid);
    }

    /**
     * Inserta una tapa fearful en la torre.
     * Solo entra si su taza compañera ya está en la torre.
     * @param cupNumber número de la taza asociada
     */
    public void pushFearfulLid(int cupNumber) {
        if (!cupIsInTower(cupNumber)) return;
        FearfulLid lid = new FearfulLid(cupNumber);
        lids.add(lid);
        towerItems.add(lid);
    }

    /**
     * Inserta una tapa crazy en la torre.
     * Se ubica en la base (índice 0) en lugar de tapar a su taza.
     * @param cupNumber número de la taza asociada
     */
    public void pushCrazyLid(int cupNumber) {
        CrazyLid lid = new CrazyLid(cupNumber);
        lids.add(lid);
        towerItems.add(0, lid);
        if (isVisible) updatePositions();
    }

    /**
     * Inserta una tapa sticky en la torre.
     * @param cupNumber número de la taza asociada
     */
    public void pushStickyLid(int cupNumber) {
        StickyLid lid = new StickyLid(cupNumber);
        lids.add(lid);
        towerItems.add(lid);
    }

    /**
     * Pone una tapa normal en todas las tazas que no tengan tapa.
     */
    public void pushLidAll() {
        for (Cup cup : cups) {
            if (!cup.hasLid()) {
                Lid lid = new Lid(cup.getNumber());
                lid.setColor("black");
                cup.putLid(lid);
            }
        }
        updatePositions();
        if (isVisible) {
            for (Cup cup : cups) {
                if (cup.hasLid()) cup.getLid().makeVisible();
            }
        }
    }

    /**
     * Pone una tapa normal en la taza del tope de la torre.
     */
    public void pushLidOnTop() {
        if (cups.isEmpty()) return;
        Cup top = cups.get(cups.size() - 1);
        if (!top.hasLid()) {
            Lid lid = new Lid(top.getNumber());
            lid.setColor("black");
            top.putLid(lid);
        }
        updatePositions();
        if (isVisible && top.hasLid()) top.getLid().makeVisible();
    }

    // -------------------------------------------------------------------------
    // Métodos de remoción
    // -------------------------------------------------------------------------

    /**
     * Remueve la taza del tope de la torre.
     * No remueve tazas hierarchical que estén en el fondo.
     */
    public void popCup() {
        if (cups.isEmpty()) return;
        Cup top = cups.get(cups.size() - 1);
        if (top instanceof HierarchicalCup && ((HierarchicalCup) top).isAtBottom()) return;
        if (top.hasLid()) lids.add(top.removeLid());
        top.makeInvisible();
        cups.remove(cups.size() - 1);
        towerItems.remove(top);
        updatePositions();
    }

    /**
     * Remueve una taza por número.
     * No remueve tazas hierarchical en el fondo ni tazas frozen congeladas.
     * @param number número de la taza a remover
     */
    public void removeCup(int number) {
        for (int i = 0; i < cups.size(); i++) {
            Cup cup = cups.get(i);
            if (cup.getNumber() != number) continue;
            if (cup instanceof HierarchicalCup && ((HierarchicalCup) cup).isAtBottom()) return;
            if (cup instanceof FrozenCup && ((FrozenCup) cup).isFrozen()) return;
            if (cup.hasLid()) lids.add(cup.removeLid());
            cup.makeInvisible();
            cups.remove(i);
            towerItems.remove(cup);
            return;
        }
    }

    /**
     * Remueve la tapa del tope de la torre.
     * No remueve tapas fearful que estén tapando a su taza.
     */
    public void popLid() {
        if (cups.isEmpty()) return;
        Cup top = cups.get(cups.size() - 1);
        if (!top.hasLid()) return;
        if (top.getLid() instanceof FearfulLid) return;
        Lid lid = top.removeLid();

        // StickyLid: arrastra la taza de debajo
        if (lid instanceof StickyLid && cups.size() >= 2) {
            Cup below = cups.get(cups.size() - 2);
            below.makeInvisible();
            cups.remove(below);
            towerItems.remove(below);
        }

        lid.makeInvisible();
        lids.add(lid);
        updatePositions();
    }

    /**
     * Remueve una tapa suelta por número de taza asociada.
     * @param cupNumber número de la taza asociada
     */
    public void removeLid(int cupNumber) {
        for (int i = 0; i < lids.size(); i++) {
            if (lids.get(i).getCupNumber() == cupNumber) {
                lids.remove(i);
                return;
            }
        }
    }

    // -------------------------------------------------------------------------
    // Métodos de consulta y utilidad
    // -------------------------------------------------------------------------

    /**
     * Intercambia dos objetos en la torre por sus identificadores.
     * No mueve tazas frozen congeladas.
     * @param o1 identificador del primer objeto [tipo, número]
     * @param o2 identificador del segundo objeto [tipo, número]
     */
    public void swap(String[] o1, String[] o2) {
        int index1 = findIndex(o1);
        int index2 = findIndex(o2);
        if (index1 == -1 || index2 == -1) return;
        Object obj1 = towerItems.get(index1);
        Object obj2 = towerItems.get(index2);
        if (obj1 instanceof FrozenCup && ((FrozenCup) obj1).isFrozen()) return;
        if (obj2 instanceof FrozenCup && ((FrozenCup) obj2).isFrozen()) return;
        Collections.swap(towerItems, index1, index2);
        if (isVisible) updatePositions();
    }

    /**
     * Sugiere un intercambio que reduciría la altura de la torre.
     * @return par de identificadores a intercambiar, o null si no hay sugerencia
     */
    public String[][] swapToReduce() {
        if (towerItems.isEmpty()) return null;
        Object first = towerItems.get(0);
        if (first instanceof Lid) return findFirstCupSwap();
        if (first instanceof Cup) return findSmallerCupOrLidSwap((Cup) first);
        return null;
    }

    /**
     * Asocia tapas sueltas a sus tazas correspondientes.
     */
    public void cover() {
        ArrayList<Lid> lidsToRemove = new ArrayList<>();
        for (Object obj : towerItems) {
            if (!(obj instanceof Cup)) continue;
            Cup cup = (Cup) obj;
            if (cup.hasLid()) continue;
            Lid lid = findLooseLid(cup.getNumber());
            if (lid == null) continue;
            lid.setColor("black");
            cup.putLid(lid);
            lids.remove(lid);
            lidsToRemove.add(lid);
        }
        towerItems.removeAll(lidsToRemove);
        updatePositions();
    }

    /**
     * Retorna la altura actual de la torre según el primer elemento.
     * @return altura calculada
     */
    public int height() {
        if (towerItems.isEmpty()) return 0;
        Object first = towerItems.get(0);
        if (first instanceof Cup) {
            return 2 * ((Cup) first).getNumber() - 1;
        }
        return 1;
    }

    /**
     * Retorna los números de las tazas que tienen tapa.
     * @return arreglo de números de tazas tapadas
     */
    public int[] lidedCups() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (Cup cup : cups) {
            if (cup.hasLid()) temp.add(cup.getNumber());
        }
        int[] result = new int[temp.size()];
        for (int i = 0; i < temp.size(); i++) result[i] = temp.get(i);
        return result;
    }

    /**
     * Retorna todos los elementos de la torre como identificadores.
     * @return matriz de [tipo, número] para cada elemento
     */
    public String[][] stackingItems() {
        String[][] result = new String[towerItems.size()][2];
        for (int i = 0; i < towerItems.size(); i++) {
            result[i] = getIdentifier(i);
        }
        return result;
    }

    /**
     * Ordena la torre colocando las tazas de mayor a menor número.
     */
    public void orderTower() {
        ArrayList<Cup> sortedCups = new ArrayList<>(cups);
        sortedCups.sort((c1, c2) -> Integer.compare(c2.getNumber(), c1.getNumber()));
        towerItems.clear();
        cups.clear();
        for (Cup cup : sortedCups) {
            cups.add(cup);
            towerItems.add(cup);
        }
        for (Lid lid : lids) towerItems.add(lid);
        if (isVisible) updatePositions();
    }

    /**
     * Invierte el orden de todos los elementos en la torre.
     */
    public void reverseTower() {
        Collections.reverse(towerItems);
        if (isVisible) updatePositions();
    }

    /**
     * Verifica si las tazas están en orden descendente (válido).
     * @return true si la torre está ordenada correctamente
     */
    public boolean ok() {
        for (int i = 0; i < cups.size() - 1; i++) {
            if (cups.get(i).getNumber() < cups.get(i + 1).getNumber()) return false;
        }
        return true;
    }

    /**
     * Libera todos los recursos y oculta la torre.
     */
    public void exit() {
        makeInvisible();
        cups.clear();
        lids.clear();
        towerItems.clear();
    }

    // -------------------------------------------------------------------------
    // Visibilidad
    // -------------------------------------------------------------------------

    /** Hace visible la torre en el canvas. */
    public void makeVisible() {
        isVisible = true;
        axisX.makeVisible();
        for (Rectangle mark : axisY) mark.makeVisible();
        for (Cup cup : cups) {
            cup.makeVisible();
            if (cup.hasLid()) cup.getLid().makeVisible();
        }
        updatePositions();
    }

    /** Hace invisible la torre en el canvas. */
    public void makeInvisible() {
        isVisible = false;
        axisX.makeInvisible();
        for (Rectangle mark : axisY) mark.makeInvisible();
        for (Cup cup : cups) {
            cup.makeInvisible();
            if (cup.hasLid()) cup.getLid().makeInvisible();
        }
    }

    // -------------------------------------------------------------------------
    // Métodos privados
    // -------------------------------------------------------------------------

    private void createAxis() {
        axisX = new Rectangle();
        axisX.changeColor("black");
        axisX.changeSize(2, 300);
        axisX.moveHorizontal(-60);
        axisX.moveVertical(265);
        for (int i = 0; i <= 9; i++) {
            Rectangle mark = new Rectangle();
            mark.changeColor("black");
            mark.changeSize(2, 15);
            mark.moveHorizontal(-60);
            mark.moveVertical(-15 + (270 - i * 30));
            axisY.add(mark);
        }
    }

    private void pushCupObject(Cup cup, int width) {
        if (cups.size() >= maxHeight) return;
        cup.setSize(30, width);
        cups.add(cup);
        towerItems.add(cup);
        if (isVisible) updatePositions();
    }

    private void removeAllLooseLids() {
        ArrayList<Lid> toRemove = new ArrayList<>(lids);
        for (Lid lid : toRemove) {
            lid.makeInvisible();
            towerItems.remove(lid);
        }
        lids.removeAll(toRemove);
    }

    private boolean cupIsInTower(int number) {
        for (Cup cup : cups) {
            if (cup.getNumber() == number) return true;
        }
        return false;
    }

    private Lid findLooseLid(int number) {
        for (Lid lid : lids) {
            if (lid.getCupNumber() == number) return lid;
        }
        return null;
    }

    private int findIndex(String[] id) {
        String type = id[0];
        int number = Integer.parseInt(id[1]);
        for (int i = 0; i < towerItems.size(); i++) {
            Object obj = towerItems.get(i);
            if (type.equals("cup") && obj instanceof Cup && ((Cup) obj).getNumber() == number) return i;
            if (type.equals("lid") && obj instanceof Lid && ((Lid) obj).getCupNumber() == number) return i;
        }
        return -1;
    }

    private String[] getIdentifier(int index) {
        Object obj = towerItems.get(index);
        if (obj instanceof Cup) return new String[]{"cup", "" + ((Cup) obj).getNumber()};
        return new String[]{"lid", "" + ((Lid) obj).getCupNumber()};
    }

    private String[][] findFirstCupSwap() {
        for (int i = 1; i < towerItems.size(); i++) {
            if (towerItems.get(i) instanceof Cup) {
                return new String[][]{ getIdentifier(0), getIdentifier(i) };
            }
        }
        return null;
    }

    private String[][] findSmallerCupOrLidSwap(Cup first) {
        int firstNum = first.getNumber();
        for (int i = 1; i < towerItems.size(); i++) {
            Object obj = towerItems.get(i);
            if (obj instanceof Cup && ((Cup) obj).getNumber() < firstNum) {
                return new String[][]{ getIdentifier(0), getIdentifier(i) };
            }
        }
        for (int i = 1; i < towerItems.size(); i++) {
            if (towerItems.get(i) instanceof Lid) {
                return new String[][]{ getIdentifier(0), getIdentifier(i) };
            }
        }
        return null;
    }

    private void updatePositions() {
        if (towerItems.isEmpty()) return;
        int baseY = 270;
        int currentY = baseY;
        for (Object obj : towerItems) {
            if (obj instanceof Cup) {
                Cup cup = (Cup) obj;
                int cupX = 45 + (width * 10 - cup.getWidth()) / 2;
                cup.setPosition(cupX, currentY);
                if (isVisible) cup.makeVisible();
                currentY -= cup.getHeight() - 10;
                if (cup.hasLid()) {
                    cup.getLid().setPosition(cupX, currentY);
                    cup.getLid().setSize(cup.getWidth());
                    if (isVisible) cup.getLid().makeVisible();
                    currentY -= 5;
                }
            } else if (obj instanceof Lid) {
                Lid lid = (Lid) obj;
                int lidX = 45 + (width * 10 - 30) / 2;
                lid.setPosition(lidX, currentY);
                lid.setSize(30);
                if (isVisible) lid.makeVisible();
                currentY -= 5;
            }
        }
    }
}
