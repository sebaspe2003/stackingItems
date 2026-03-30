package tower;

import javax.swing.JOptionPane;

/**
 * Pruebas de aceptación del Ciclo 4.
 * Cada prueba muestra visualmente el comportamiento y pregunta al usuario
 * si lo acepta.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class TowerATest {

    private static final int WAIT_MS = 1500;

    /**
     * Prueba de aceptación 1: OpenerCup elimina tapas sueltas al entrar.
     * Se insertan tapas sueltas y luego una OpenerCup; el usuario verifica
     * que las tapas desaparecen.
     */
    public static void acceptanceTestOpenerCup() {
        Tower t = new Tower(9, 100);
        t.pushCup(3, 30);
        t.pushCup(1, 10);
        t.pushLid(3);
        t.pushLid(1);
        t.makeVisible();
        sleep(WAIT_MS);

        t.pushOpenerCup(5, 50);
        sleep(WAIT_MS);

        int result = JOptionPane.showConfirmDialog(null,
            "¿Las tapas sueltas desaparecieron al entrar la OpenerCup (naranja)?",
            "Prueba de Aceptación 1 - OpenerCup",
            JOptionPane.YES_NO_OPTION);

        t.exit();
        assert result == JOptionPane.YES_OPTION : "Prueba de aceptación 1 RECHAZADA";
    }

    /**
     * Prueba de aceptación 2: CrazyLid va a la base y FrozenCup no se mueve.
     * Se inserta una FrozenCup, se intenta hacer swap y se inserta una CrazyLid;
     * el usuario verifica que la CrazyLid está en la base y la FrozenCup no se movió.
     */
    public static void acceptanceTestCrazyAndFrozen() {
        Tower t = new Tower(9, 100);
        t.pushCup(4, 40);
        t.pushFrozenCup(2, 20);
        t.pushCup(1, 10);
        t.makeVisible();
        sleep(WAIT_MS);

        // Intentar mover la FrozenCup — no debe moverse
        t.swap(new String[]{"cup", "2"}, new String[]{"cup", "1"});
        sleep(WAIT_MS);

        // Insertar CrazyLid — debe ir a la base
        t.pushCrazyLid(4);
        sleep(WAIT_MS);

        int result = JOptionPane.showConfirmDialog(null,
            "¿La tapa roja (CrazyLid) está en la BASE de la torre\n" +
            "y la taza gris (FrozenCup) NO se movió con el swap?",
            "Prueba de Aceptación 2 - CrazyLid + FrozenCup",
            JOptionPane.YES_NO_OPTION);

        t.exit();
        assert result == JOptionPane.YES_OPTION : "Prueba de aceptación 2 RECHAZADA";
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Ejecuta ambas pruebas de aceptación.
     * @param args argumentos de línea de comandos (no usados)
     */
    public static void main(String[] args) {
        acceptanceTestOpenerCup();
        acceptanceTestCrazyAndFrozen();
    }
}
