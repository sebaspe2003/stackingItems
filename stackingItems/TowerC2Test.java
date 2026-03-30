import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Pruebas unitarias correspondientes al Ciclo 2 del proyecto.
 * 
 * Verifican el correcto funcionamiento de los nuevos métodos
 * implementados en la clase Tower.
 * 
 * Las pruebas se ejecutan en modo invisible, es decir,
 * no se invoca makeVisible().
 * 
 * Se validan:
 * - Creación de la torre
 * - Métodos de intercambio (swap)
 * - Cobertura de tazas
 * - Reducción de altura mediante intercambio
 * 
 * Incluye casos positivos y negativos.
 * 
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 2
 */
public class TowerC2Test {


    private boolean containsCup(int[] arr, int value) {
        for (int x : arr) {
            if (x == value) return true;
        }
        return false;
    }

    private boolean sameIdentifier(String[] id, String type, String num) {
        return id != null && id.length == 2 && type.equals(id[0]) && num.equals(id[1]);
    }

    // --------------------------
    // Requisito 10: Tower(int cups)
    // --------------------------

    @Test
    public void shouldCreateTowerWithGivenNumberOfCups() {
        Tower t = new Tower(4); 

        String[][] items = t.stackingItems();
        assertNotNull(items);
        assertEquals("Debe haber 4 objetos (4 cups)", 4, items.length);

        
        assertTrue("Primero debería ser cup 4", sameIdentifier(items[0], "cup", "4"));
        assertTrue("Segundo debería ser cup 3", sameIdentifier(items[1], "cup", "3"));
        assertTrue("Tercero debería ser cup 2", sameIdentifier(items[2], "cup", "2"));
        assertTrue("Cuarto debería ser cup 1", sameIdentifier(items[3], "cup", "1"));

        
        int[] lided = t.lidedCups();
        assertNotNull(lided);
        assertEquals("No debería haber tazas tapadas al inicio", 0, lided.length);
    }

    // --------------------------
    // Requisito 12: cover()
    // --------------------------

    @Test
    public void shouldCoverCupsWhenTheirLooseLidsExist() {
        Tower t = new Tower(3);   
        t.pushLid(2);             
        t.pushLid(3);             

        t.cover();

        int[] lided = t.lidedCups();
        assertEquals("Debería haber 2 tazas tapadas", 2, lided.length);
        assertTrue("Cup 2 debería estar tapada", containsCup(lided, 2));
        assertTrue("Cup 3 debería estar tapada", containsCup(lided, 3));

        
        String[][] items = t.stackingItems();
        for (int i = 0; i < items.length; i++) {
            assertFalse("No debería quedar lid 2 suelta", sameIdentifier(items[i], "lid", "2"));
            assertFalse("No debería quedar lid 3 suelta", sameIdentifier(items[i], "lid", "3"));
        }
    }

    @Test
    public void shouldNotCoverIfThereIsNoMatchingLid() {
        Tower t = new Tower(3);
        t.pushLid(99); // 

        t.cover();

        int[] lided = t.lidedCups();
        assertEquals("No debería tapar ninguna taza si no hay lid correspondiente", 0, lided.length);
    }

    // --------------------------
    // Requisito 11: swap(o1,o2)
    // --------------------------

    @Test
    public void shouldSwapTwoExistingObjectsInTower() {
        Tower t = new Tower(3);
        t.pushLid(2);            

        String[][] before = t.stackingItems();
        assertEquals(4, before.length);

        
        t.swap(new String[]{"cup","1"}, new String[]{"lid","2"});

        String[][] after = t.stackingItems();
        assertEquals("Debe mantenerse el mismo número de objetos", 4, after.length);

        
        assertFalse("Después del swap, el primer item no debería seguir siendo cup 1",
                sameIdentifier(after[0], "cup", "1"));
    }

    @Test
    public void shouldNotSwapIfAnyIdentifierDoesNotExist() {
        Tower t = new Tower(3); 
        String[][] before = t.stackingItems();

        
        t.swap(new String[]{"cup","99"}, new String[]{"cup","1"});

        String[][] after = t.stackingItems();

        
        assertEquals(before.length, after.length);
        for (int i = 0; i < before.length; i++) {
            assertEquals(before[i][0], after[i][0]);
            assertEquals(before[i][1], after[i][1]);
        }
    }

    // --------------------------
    // Requisito 13: swapToReduce()
    // --------------------------

    @Test
    public void shouldSuggestASwapThatReducesHeightWhenPossible() {
        Tower t = new Tower(4);

        // Creamos una configuración donde cambiar el orden puede afectar height()
        
        t.pushLid(1);
        t.pushLid(4);

        int h1 = t.height();

        String[][] suggestion = t.swapToReduce();

        
        
        assertNotNull("Debería sugerir un swap cuando sea posible reducir altura", suggestion);
        assertEquals(2, suggestion.length);
        assertEquals(2, suggestion[0].length);
        assertEquals(2, suggestion[1].length);

        
        t.swap(suggestion[0], suggestion[1]);
        int h2 = t.height();

        assertTrue("Luego de aplicar el swap sugerido, la altura debería disminuir", h2 < h1);
    }

    @Test
    public void shouldReturnNullWhenNoSwapReducesHeight() {
        Tower t = new Tower(1); 

        String[][] suggestion = t.swapToReduce();
        assertNull("Con 1 solo objeto, no debería haber swap que reduzca altura", suggestion);
    }
}
