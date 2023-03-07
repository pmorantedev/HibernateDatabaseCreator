package entitats;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import java.sql.Date;

/**
 * Classe abstracta per representar objectes de tipus Autonoma.
 * 
 * @author Pablo Morante: Creació/Implementació
 * @author Víctor García: Implementació
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="autonoma")
public abstract class Autonoma extends Aeronau {

    @Column(nullable = false)
    private boolean invisibilityPower;
    @Column(nullable = false)
    private int batteryAutonomy;

    public Autonoma(){
        
    }
    
    public Autonoma(boolean invisibilityPower, int batteryAutonomy, 
                    String corporation, Float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        super(corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.invisibilityPower = invisibilityPower;
        this.batteryAutonomy = batteryAutonomy;
    }

    public boolean isInvisibilityPower() {
        return invisibilityPower;
    }

    public int getBatteryAutonomy() {
        return batteryAutonomy;
    }

    public void setInvisibilityPower(boolean invisibilityPower) {
        this.invisibilityPower = invisibilityPower;
    }

    public void setBatteryAutonomy(int batteryAutonomy) {
        this.batteryAutonomy = batteryAutonomy;
    }

}
