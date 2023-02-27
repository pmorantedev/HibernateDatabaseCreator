package entitats;

import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.BatchSize;

/**
 *
 * @author pablomorante
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pilotada")
public abstract class Pilotada extends Aeronau {

    @Column(nullable = false)
    private boolean hasEjectoSeat;
    @Column(nullable = false)
    private Float shellCapacity;

    // (RF04) Cardinalitat No Propietària (Costat invers)
    @OneToOne(mappedBy = "pilotada", fetch = FetchType.LAZY)  // Entitat inversa a la relació, LAZY evita carregar aquest registre de nau Pilotada si no es demana expressament (no sobresatura la BD)
    private Pilot pilot;

//  (RF05)
    @OneToMany(mappedBy = "pilotada")
    private List<Mecanic> mecanics = new ArrayList<Mecanic>(2);

    public Pilotada() {

    }

    public Pilotada(boolean hasEjectoSeat, Float shellCapacity, String corporation,
            Float engineTorque, Date autodestructionDate, Boolean hasDeathLaser,
            Pilot pilot) {
        super(corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.hasEjectoSeat = hasEjectoSeat;
        this.shellCapacity = shellCapacity;
        this.pilot = pilot;
    }

    public Pilotada(boolean hasEjectoSeat, Float shellCapacity, String corporation,
            Float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        super(corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.hasEjectoSeat = hasEjectoSeat;
        this.shellCapacity = shellCapacity;
    }

    // Getters, Setters
    /**
     * (RF04) Mètode que obté la Clau Forània (FK) amb la que està
     * relacionada.Relació (0,1 : 0,1) bidireccional amb la classe 'Pilot'.
     *
     * @author Txell Llanas: Creació/Implementació
     * @return pilot Instància que defineix el 'Pilot' que pilota aquesta nau.
     */
    public Pilot getPilotAeronau() {
        return pilot;
    }

    /**
     * (RF04) Mètode que estableix la Clau Forània (FK) amb la que està
     * relacionada. Relació (0,1 : 0,1) bidireccional amb la classe 'Pilot'.
     *
     * @author Txell Llanas: Creació/Implementació
     */
    public void setPilotAeronau(Pilot pilot) {
        this.pilot = pilot;
    }

    public void setMecanics(List<Mecanic> mecanics) {
        this.mecanics = mecanics;
    }

    public List<Mecanic> getMecanics() {
        return mecanics;
    }

    public boolean getHasEjectoSeat() {
        return hasEjectoSeat;
    }

    public Float getShellCapacity() {
        return shellCapacity;
    }

    public void setHasEjectoSeat(boolean hasEjectoSeat) {
        this.hasEjectoSeat = hasEjectoSeat;
    }

    public void setShellCapacity(Float shellCapacity) {
        this.shellCapacity = shellCapacity;
    }

}
