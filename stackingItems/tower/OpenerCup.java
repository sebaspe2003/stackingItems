package tower;

/**
 * Taza de tipo opener: al entrar a la torre elimina todas las tapas
 * que le impiden el paso (tapas de tazas más pequeñas que estén encima).
 * Se distingue visualmente con color naranja.
 *
 * @author Nicolás Prieto
 * @author Sebastian Peña
 * @version Ciclo 4
 */
public class OpenerCup extends Cup {

    /**
     * Crea una taza opener con el número identificador dado.
     * @param number número identificador
     */
    public OpenerCup(int number) {
        super(number);
        setColor("orange");
    }

    /**
     * {@inheritDoc}
     * @return "opener"
     */
    @Override
    public String getType() {
        return "opener";
    }
}
