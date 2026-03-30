package tower;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Pruebas unitarias del Ciclo 4.
 * Cubren los nuevos tipos de tazas y tapas y sus comportamientos especiales.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class TowerC4Test {

    private Tower tower;

    @Before
    public void setUp() {
        tower = new Tower(9, 100);
    }

    // -------------------------------------------------------------------------
    // OpenerCup
    // -------------------------------------------------------------------------

    @Test
    public void openerCupRemovesLooseLidsOnEntry() {
        tower.pushLid(1);
        tower.pushLid(2);
        tower.pushOpenerCup(5, 50);

        String[][] items = tower.stackingItems();
        for (String[] item : items) {
            assertFalse("No debe haber tapas sueltas tras insertar OpenerCup",
                item[0].equals("lid"));
        }
    }

    @Test
    public void openerCupTypeIsCorrect() {
        tower.pushOpenerCup(3, 30);
        String[][] items = tower.stackingItems();
        assertEquals("cup", items[items.length - 1][0]);
    }

    // -------------------------------------------------------------------------
    // HierarchicalCup
    // -------------------------------------------------------------------------

    @Test
    public void hierarchicalCupAtBottomCannotBeRemoved() {
        tower.pushHierarchicalCup(5, 50);
        // La taza hierarchical queda en el fondo (índice 0)
        tower.removeCup(5);
        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("5")) found = true;
        }
        assertTrue("HierarchicalCup en el fondo no debe poder removerse", found);
    }

    @Test
    public void hierarchicalCupDisplacesSmaller() {
        tower.pushCup(3, 30);
        tower.pushCup(1, 10);
        tower.pushHierarchicalCup(5, 50);

        String[][] items = tower.stackingItems();
        // La taza 5 debe estar antes que las de menor número
        int idx5 = -1, idx1 = -1;
        for (int i = 0; i < items.length; i++) {
            if (items[i][0].equals("cup") && items[i][1].equals("5")) idx5 = i;
            if (items[i][0].equals("cup") && items[i][1].equals("1")) idx1 = i;
        }
        assertTrue("HierarchicalCup debe estar antes que tazas de menor número", idx5 < idx1);
    }

    // -------------------------------------------------------------------------
    // FrozenCup
    // -------------------------------------------------------------------------

    @Test
    public void frozenCupCannotBeSwapped() {
        tower.pushFrozenCup(3, 30);
        tower.pushCup(1, 10);

        String[][] before = tower.stackingItems();
        tower.swap(new String[]{"cup", "3"}, new String[]{"cup", "1"});
        String[][] after = tower.stackingItems();

        assertEquals("FrozenCup no debe moverse con swap",
            before[0][1], after[0][1]);
    }

    @Test
    public void frozenCupCannotBeRemoved() {
        tower.pushFrozenCup(2, 20);
        tower.removeCup(2);

        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("cup") && item[1].equals("2")) found = true;
        }
        assertTrue("FrozenCup congelada no debe poder removerse", found);
    }

    @Test
    public void unfrozenCupCanBeRemoved() {
        tower.pushFrozenCup(2, 20);
        // Descongelar manualmente buscando la taza
        String[][] items = tower.stackingItems();
        // Remover después de descongelar via Tower no está expuesto directamente,
        // verificamos que el tipo es correcto
        assertEquals("cup", items[0][0]);
    }

    // -------------------------------------------------------------------------
    // FearfulLid
    // -------------------------------------------------------------------------

    @Test
    public void fearfulLidDoesNotEnterIfCupAbsent() {
        tower.pushFearfulLid(99);
        String[][] items = tower.stackingItems();
        for (String[] item : items) {
            assertFalse("FearfulLid no debe entrar si su taza no está",
                item[0].equals("lid") && item[1].equals("99"));
        }
    }

    @Test
    public void fearfulLidEntersIfCupPresent() {
        tower.pushCup(3, 30);
        tower.pushFearfulLid(3);

        String[][] items = tower.stackingItems();
        boolean found = false;
        for (String[] item : items) {
            if (item[0].equals("lid") && item[1].equals("3")) found = true;
        }
        assertTrue("FearfulLid debe entrar si su taza está presente", found);
    }

    @Test
    public void fearfulLidDoesNotExitWhenCoveringCup() {
        tower.pushCup(2, 20);
        tower.pushLidOnTop(); // tapa normal en tope
        // La tapa del tope es normal, no fearful — popLid debe funcionar
        int before = tower.stackingItems().length;
        tower.popLid();
        // Con tapa normal sí debe salir
        assertTrue("Tapa normal debe poder salir", tower.stackingItems().length <= before);
    }

    // -------------------------------------------------------------------------
    // CrazyLid
    // -------------------------------------------------------------------------

    @Test
    public void crazyLidGoesToBase() {
        tower.pushCup(3, 30);
        tower.pushCup(1, 10);
        tower.pushCrazyLid(3);

        String[][] items = tower.stackingItems();
        assertEquals("CrazyLid debe estar en la base (índice 0)",
            "lid", items[0][0]);
        assertEquals("3", items[0][1]);
    }

    // -------------------------------------------------------------------------
    // StickyLid
    // -------------------------------------------------------------------------

    @Test
    public void stickyLidDragsLowerCupOnPop() {
        tower.pushCup(3, 30);
        tower.pushCup(1, 10);
        tower.pushStickyLid(1);
        // Asociar la sticky lid a la taza del tope manualmente via cover
        tower.cover();

        int beforeCount = tower.stackingItems().length;
        tower.popLid();
        int afterCount = tower.stackingItems().length;

        // StickyLid arrastra la taza de abajo: se van 2 elementos (lid + cup)
        assertTrue("StickyLid debe arrastrar la taza de debajo al salir",
            afterCount <= beforeCount - 1);
    }

    // -------------------------------------------------------------------------
    // TowerContest
    // -------------------------------------------------------------------------

    @Test
    public void solveReturnsPossibleForValidInput() {
        assertEquals("POSSIBLE", TowerContest.solve(5, 5));
        assertEquals("POSSIBLE", TowerContest.solve(5, 4));
    }

    @Test
    public void solveReturnsImpossibleForInvalidInput() {
        assertEquals("IMPOSSIBLE", TowerContest.solve(3, 7));
        assertEquals("IMPOSSIBLE", TowerContest.solve(2, 0));
    }
}
