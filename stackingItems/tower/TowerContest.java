package tower;

import java.util.logging.Logger;

/**
 * Resuelve y simula el problema de la maratón Stacking Cups.
 * Solo usa tazas y tapas normales según el enunciado del ciclo 4.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class TowerContest {

    private static final Logger LOGGER = Logger.getLogger(TowerContest.class.getName());
    private static Tower currentTower = null;

    /**
     * Determina si es posible construir una torre de altura h con n tazas.
     * @param n número de tazas disponibles
     * @param h altura objetivo
     * @return "POSSIBLE" o "IMPOSSIBLE"
     */
    public static String solve(int n, int h) {
        if (h < 1 || h > 2 * n) return "IMPOSSIBLE";
        if (h % 2 == 1) return "POSSIBLE";
        if (n >= h / 2) return "POSSIBLE";
        return "IMPOSSIBLE";
    }

    /**
     * Simula visualmente la solución usando tazas y tapas normales.
     * @param n número de tazas disponibles
     * @param h altura objetivo
     */
    public static void simulate(int n, int h) {
        if (currentTower != null) {
            currentTower.exit();
            currentTower = null;
        }
        if (solve(n, h).equals("IMPOSSIBLE")) {
            LOGGER.info("IMPOSSIBLE - No se puede simular una solucion que no existe.");
            return;
        }
        if (h > 30) {
            LOGGER.info("POSSIBLE - Altura demasiado grande para graficar.");
            return;
        }
        currentTower = new Tower(n, h);
        int cupsNeeded = (h % 2 == 1) ? (h + 1) / 2 : h / 2;
        int maxWidth = Math.min(250, cupsNeeded * 20);
        for (int i = cupsNeeded; i >= 1; i--) {
            int w = (cupsNeeded == 1) ? maxWidth
                : (int) ((2.0 * i - 1) / (2.0 * cupsNeeded - 1) * maxWidth);
            if (w < 10) w = 10;
            currentTower.pushCup(i, w);
        }
        currentTower.pushLidAll();
        currentTower.makeVisible();
        LOGGER.info("POSSIBLE - Solucion simulada con altura " + currentTower.height());
    }
}
