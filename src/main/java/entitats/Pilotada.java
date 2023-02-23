package entitats;

import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.sql.Date;
import org.hibernate.mapping.List;

/**
 *
 * @author pablomorante
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="pilotada")
public abstract class Pilotada extends Aeronau {
    
    @Column(nullable = false)
    private boolean hasEjectoSeat;
    @Column(nullable = false)
    private float shellCapacity;
    
    // (RF04) Cardinalitat No Propietària (Costat invers)
    @OneToOne(mappedBy="pilotada", fetch = FetchType.LAZY)  // Entitat inversa a la relació, LAZY evita carregar aquest registre de nau Pilotada si no es demana expressament (no sobresatura la BD)
    private Pilot pilot;

    // (RF05)
//    @OneToMany(mappedBy = "pilotada")
//    @JoinColumn(name = "pilotada_id")
//    private List<Mecanic> mecanics = new ArrayList<Mecanic>(2);
    
    public Pilotada(){
        
    }
    
    public Pilotada(boolean hasEjectoSeat, float shellCapacity, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser, Pilot pilot) {
        super(corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.hasEjectoSeat = hasEjectoSeat;
        this.shellCapacity = shellCapacity;
        this.pilot = pilot;
    }

    // Getters, Setters
    /**
     * (RF04) Mètode que obté la Clau Forània (FK) amb la que està relacionada.Relació (0,1 : 0,1) bidireccional amb la classe 'Pilot'.
     * 
     * @author Txell Llanas: Creació/Implementació
     * @return pilot Instància que defineix el 'Pilot' que pilota aquesta nau.
     */
    public Pilot getPilotAeronau() {
        return pilot;
    }
    /**
     * (RF04) Mètode que estableix la Clau Forània (FK) amb la que està relacionada.
     * Relació (0,1 : 0,1) bidireccional amb la classe 'Pilot'.
     * 
     * @author Txell Llanas: Creació/Implementació
     */
    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
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

    // (RF05)
//    @OneToMany(mappedBy = "pilotada")
//    private List<Mecanic> mecanics = new ArrayList<Mecanic>(2);
