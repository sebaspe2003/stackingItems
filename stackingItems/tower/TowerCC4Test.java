package tower;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas de casos comunes del Ciclo 4 (creación colectiva).
 * Verifican la interacción entre los distintos tipos de tazas y tapas.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class TowerCC4Test {

    // -------------------------------------------------------------------------
    // Interacción OpenerCup + FearfulLid
    // -------------------------------------------------------------------------

    @Test
    public void openerCupRemovesFearfulLidsOnEntry() {
        Tower t = new Tower(9, 100);
        t.pushCup(2, 20);
        t.pushFearfulLid(2);
        t.pushOpenerCup(5, 50);

        String[][] items = t.stackingItems();
        for (String[] item : items) {
            assertFalse("OpenerCup debe eliminar tapas fearful sueltas",
                item[0].equals("lid"));
        }
    }

    // -------------------------------------------------------------------------
    // Interacción HierarchicalCup + CrazyLid
    // -------------------------------------------------------------------------

    @Test
    public void crazyLidGoesToBaseEvenWithHierarchicalCup() {
        Tower t = new Tower(9, 100);
        t.pushHierarchicalCup(5, 50);
        t.pushCrazyLid(5);

        String[][] items = t.stackingItems();
        assertEquals("CrazyLid debe ir a la base incluso con HierarchicalCup",
            "lid", items[0][0]);
    }

    // -------------------------------------------------------------------------
    // Interacción FrozenCup + swap
    // -------------------------------------------------------------------------

    @Test
    public void swapBetweenTwoNormalCupsWorks() {
        Tower t = new Tower(9, 100);
        t.pushCup(3, 30);
        t.pushCup(1, 10);

        t.swap(new String[]{"cup", "3"}, new String[]{"cup", "1"});

        String[][] items = t.stackingItems();
        assertEquals("Después del swap, cup 1 debe estar primero", "1", items[0][1]);
    }

    @Test
    public void swapWithFrozenCupDoesNothing() {
        Tower t = new Tower(9, 100);
        t.pushFrozenCup(3, 30);
        t.pushCup(1, 10);

        String[][] before = t.stackingItems();
        t.swap(new String[]{"cup", "3"}, new String[]{"cup", "1"});
        String[][] after = t.stackingItems();

        assertEquals(before[0][1], after[0][1]);
        assertEquals(before[1][1], after[1][1]);
    }

    // -------------------------------------------------------------------------
    // Torre mixta: varios tipos
    // -------------------------------------------------------------------------

    @Test
    public void mixedTowerHasCorrectItemCount() {
        Tower t = new Tower(9, 100);
        t.pushCup(4, 40);
        t.pushOpenerCup(3, 30);
        t.pushHierarchicalCup(5, 50);
        t.pushFrozenCup(2, 20);

        String[][] items = t.stackingItems();
        assertEquals("Deben existir 4 tazas en la torre", 4, items.length);
    }

    @Test
    public void mixedLidsHaveCorrectCount() {
        Tower t = new Tower(9, 100);
        t.pushCup(3, 30);
        t.pushCup(2, 20);
        t.pushCup(1, 10);
        t.pushFearfulLid(3);
        t.pushCrazyLid(2);
        t.pushStickyLid(1);

        String[][] items = t.stackingItems();
        int lidCount = 0;
        for (String[] item : items) {
            if (item[0].equals("lid")) lidCount++;
        }
        assertEquals("Deben existir 3 tapas sueltas", 3, lidCount);
    }

    // -------------------------------------------------------------------------
    // TowerContest con valores límite
    // -------------------------------------------------------------------------

    @Test
    public void solveEdgeCaseHEquals1() {
        assertEquals("POSSIBLE", TowerContest.solve(1, 1));
    }

    @Test
    public void solveEdgeCaseHEquals2N() {
        assertEquals("POSSIBLE", TowerContest.solve(3, 6));
    }

    @Test
    public void solveEdgeCaseHGreaterThan2N() {
        assertEquals("IMPOSSIBLE", TowerContest.solve(3, 7));
    }
}
