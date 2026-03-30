package tower;

/**
 * Taza de tipo frozen (propuesta propia): congela su posición en la torre,
 * impidiendo que cualquier swap la mueva mientras esté activa.
 * Se distingue visualmente con color gray.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class FrozenCup extends Cup {

    private boolean frozen;

    /**
     * Crea una taza frozen con el número identificador dado.
     * @param number número identificador
     */
    public FrozenCup(int number) {
        super(number);
        setColor("gray");
        this.frozen = true;
    }

    /**
     * {@inheritDoc}
     * @return "frozen"
     */
    @Override
    public String getType() {
        return "frozen";
    }

    /**
     * Indica si la taza está congelada (no puede ser movida por swap).
     * @return true si está congelada
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * Cambia el estado de congelamiento de la taza.
     * @param frozen true para congelar, false para descongelar
     */
    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
        setColor(frozen ? "gray" : "blue");
    }
}
