package tower;

/**
 * Tapa de tipo crazy: en lugar de tapar a su taza, se ubica en la base
 * de la torre al ser insertada.
 * Se distingue visualmente con color red.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class CrazyLid extends Lid {

    /**
     * Crea una tapa crazy asociada al número de taza dado.
     * @param cupNumber número de la taza asociada
     */
    public CrazyLid(int cupNumber) {
        super(cupNumber);
        setColor("red");
    }

    /**
     * {@inheritDoc}
     * @return "crazy"
     */
    @Override
    public String getType() {
        return "crazy";
    }
}
