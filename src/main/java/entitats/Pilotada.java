package entitats;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import java.sql.Date;

/**
 *
 * @author pablomorante
 */
@Entity
public abstract class Pilotada extends Aeronau {
    
    @Column(nullable = false)
    private boolean hasEjectoSeat;
    @Column(nullable = false)
    private float shellCapacity;
    
    // (RF04) Cardinalitat No Propietària (Costat invers)
    @OneToOne(mappedBy="pilotada")  // Entitat inversa a la relació
    private Pilot pilot;

    public Pilotada(boolean hasEjectoSeat, float shellCapacity, int fabricationNumber, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        super(fabricationNumber, corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.hasEjectoSeat = hasEjectoSeat;
        this.shellCapacity = shellCapacity;
    }

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
    
    public boolean getHasEjectoSeat() {
        return hasEjectoSeat;
    }

    public float getShellCapacity() {
        return shellCapacity;
    }

    public void setHasEjectoSeat(boolean hasEjectoSeat) {
        this.hasEjectoSeat = hasEjectoSeat;
    }

    public void setShellCapacity(float shellCapacity) {
        this.shellCapacity = shellCapacity;
    }

}
