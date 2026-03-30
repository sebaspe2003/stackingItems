package tower;

/**
 * Tapa de tipo fearful: si su taza compañera no está en la torre, no entra.
 * Si está tapando a su taza, no sale.
 * Se distingue visualmente con color magenta.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class FearfulLid extends Lid {

    /**
     * Crea una tapa fearful asociada al número de taza dado.
     * @param cupNumber número de la taza asociada
     */
    public FearfulLid(int cupNumber) {
        super(cupNumber);
        setColor("magenta");
    }

    /**
     * {@inheritDoc}
     * @return "fearful"
     */
    @Override
    public String getType() {
        return "fearful";
    }
}
