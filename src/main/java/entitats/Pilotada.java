package entitats;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

/**
 *
 * @author pablomorante
 */
public abstract class Pilotada extends Aeronau {

    private boolean hasEjectoSeat;
    private float shellCapacity;

    // (RF04) Cardinalitat No Propietària (Costat invers)
    @OneToOne(mappedBy="pilotada")  // Entitat inversa a la relació
    private Pilot pilot;
    
    // Getters, Setters
    /**
     * (RF04) Mètode que obté la Clau Forània (FK) amb la que està relacionada.
     * Relació (0,1 : 0,1) bidireccional amb la classe 'Pilot'.
     * 
     * @param pilot Instància que defineix el 'Pilot' que pilota aquesta nau.
     * @author Txell Llanas: Creació/Implementació
     */
    public Pilot getPilot() {
        return pilot;
    }
    
}
