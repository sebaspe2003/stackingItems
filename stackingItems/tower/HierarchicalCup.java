package tower;

/**
 * Taza de tipo hierarchical: al entrar a la torre desplaza todos los
 * objetos de menor tamaño hacia arriba. Si llega al fondo de la torre,
 * no puede ser removida.
 * Se distingue visualmente con color cyan.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class HierarchicalCup extends Cup {

    private boolean atBottom;

    /**
     * Crea una taza hierarchical con el número identificador dado.
     * @param number número identificador
     */
    public HierarchicalCup(int number) {
        super(number);
        setColor("cyan");
        this.atBottom = false;
    }

    /**
     * {@inheritDoc}
     * @return "hierarchical"
     */
    @Override
    public String getType() {
        return "hierarchical";
    }

    /**
     * Marca esta taza como ubicada en el fondo de la torre.
     * Una vez en el fondo, no puede ser removida.
     * @param atBottom true si está en el fondo
     */
    public void setAtBottom(boolean atBottom) {
        this.atBottom = atBottom;
    }

    /**
     * Indica si la taza está en el fondo y no puede removerse.
     * @return true si está bloqueada en el fondo
     */
    public boolean isAtBottom() {
        return atBottom;
    }
}
