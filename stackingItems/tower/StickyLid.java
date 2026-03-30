package tower;

/**
 * Tapa de tipo sticky (propuesta propia): al salir de la torre arrastra
 * consigo la taza que estaba inmediatamente debajo de ella.
 * Se distingue visualmente con color green.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class StickyLid extends Lid {

    /**
     * Crea una tapa sticky asociada al número de taza dado.
     * @param cupNumber número de la taza asociada
     */
    public StickyLid(int cupNumber) {
        super(cupNumber);
        setColor("green");
    }

    /**
     * {@inheritDoc}
     * @return "sticky"
     */
    @Override
    public String getType() {
        return "sticky";
    }
}
