package entitats;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import java.sql.Date;

/**
 *
 * @author pablomorante
 */
@MappedSuperclass
public abstract class Autonoma extends Aeronau {

    @Column(nullable = false)
    private boolean invisibilityPower;
    @Column(nullable = false)
    private int batteryAutonomy;

    public Autonoma(){
        
    }
    
    public Autonoma(boolean invisibilityPower, int batteryAutonomy, int fabricationNumber, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        super(fabricationNumber, corporation, engineTorque, autodestructionDate, hasDeathLaser);
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
