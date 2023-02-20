package entitats;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;
import java.sql.Date;

/**
 *
 * @author pablomorante
 */
public abstract class Pilotada extends Aeronau {

    @Column(nullable = false)
    private boolean hasEjectoSeat;
    @Column(nullable = false)
    private float shellCapacity;
    @ManyToMany
    private Pilot pilots;

    public Pilotada(boolean hasEjectoSeat, float shellCapacity, int fabricationNumber, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        super(fabricationNumber, corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.hasEjectoSeat = hasEjectoSeat;
        this.shellCapacity = shellCapacity;
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
