import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 * Clase de pruebas común para TowerContest.
 * Casos de prueba compartidos para verificar la solución.
 * 
 * @autores Nicolás Prieto y Sebastian Peña
 * @version 3
 */
public class TowerContestCTest {
    
    /**
     * Caso de prueba 1: n=1, h=1
     * Debe ser POSSIBLE (una tapa suelta)
     */
    @Test
    public void testCase01() {
        assertEquals("POSSIBLE", TowerContest.solve(1, 1));
    }
    
    /**
     * Caso de prueba 2: n=2, h=1
     * Debe ser POSSIBLE (una tapa suelta)
     */
    @Test
    public void testCase02() {
        assertEquals("POSSIBLE", TowerContest.solve(2, 1));
    }
    
    /**
     * Caso de prueba 3: n=2, h=2
     * Debe ser POSSIBLE (una taza con tapa)
     */
    @Test
    public void testCase03() {
        assertEquals("POSSIBLE", TowerContest.solve(2, 2));
    }
    
    /**
     * Caso de prueba 4: n=2, h=3
     * Debe ser POSSIBLE (dos tazas sin tapas)
     */
    @Test
    public void testCase04() {
        assertEquals("POSSIBLE", TowerContest.solve(2, 3));
    }
    
    /**
     * Caso de prueba 5: n=3, h=4
     * Debe ser POSSIBLE (dos tazas con tapas)
     */
    @Test
    public void testCase05() {
        assertEquals("POSSIBLE", TowerContest.solve(3, 4));
    }
    
    /**
     * Caso de prueba 6: n=3, h=6
     * Debe ser POSSIBLE (3 tazas con tapas = altura 6)
     */
    @Test
    public void testCase06() {
        assertEquals("POSSIBLE", TowerContest.solve(3, 6));
    }
    
    /**
     * Caso de prueba 7: n=5, h=9
     * Debe ser POSSIBLE (altura máxima con 5 tazas sin tapas)
     */
    @Test
    public void testCase07() {
        assertEquals("POSSIBLE", TowerContest.solve(5, 9));
    }
    
    /**
     * Caso de prueba 8: n=5, h=10
     * Debe ser POSSIBLE (5 tazas con tapas = altura 10)
     */
    @Test
    public void testCase08() {
        assertEquals("POSSIBLE", TowerContest.solve(5, 10));
    }
    
    /**
     * Caso de prueba 9: n=10, h=15
     * Debe ser POSSIBLE (altura impar)
     */
    @Test
    public void testCase09() {
        assertEquals("POSSIBLE", TowerContest.solve(10, 15));
    }
    
    /**
     * Caso de prueba 10: n=10, h=20
     * Debe ser POSSIBLE (10 tazas con tapas)
     */
    @Test
    public void testCase10() {
        assertEquals("POSSIBLE", TowerContest.solve(10, 20));
    }
    
    /**
     * Caso de prueba 11: n=4, h=0
     * Debe ser IMPOSSIBLE (altura menor a 1)
     */
    @Test
    public void testCase11() {
        assertEquals("IMPOSSIBLE", TowerContest.solve(4, 0));
    }
    
    /**
     * Caso de prueba 12: n=4, h=8
     * Debe ser POSSIBLE (4 tazas con tapas)
     */
    @Test
    public void testCase12() {
        assertEquals("POSSIBLE", TowerContest.solve(4, 8));
    }
}
