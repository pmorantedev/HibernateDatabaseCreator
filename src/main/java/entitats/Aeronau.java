package entitats;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Date;

/**
 *
 * @author pablomorante
 */
@MappedSuperclass
public abstract class Aeronau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int fabricationNumber;
    @Column(nullable = false)
    private String corporation;
    @Column(nullable = false)
    private float engineTorque;
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date autodestructionDate;
    @Column(nullable = false)
    private Boolean hasDeathLaser;

    public Aeronau(int fabricationNumber, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        this.fabricationNumber = fabricationNumber;
        this.corporation = corporation;
        this.engineTorque = engineTorque;
        this.autodestructionDate = autodestructionDate;
        this.hasDeathLaser = hasDeathLaser;
    }

    public int getFabricationNumber() {
        return fabricationNumber;
    }

    public String getCorporation() {
        return corporation;
    }

    public float getEngineTorque() {
        return engineTorque;
    }

    public Date getAutodestructionDate() {
        return autodestructionDate;
    }

    public Boolean getHasDeathLaser() {
        return hasDeathLaser;
    }

    public void setFabricationNumber(int fabricationNumber) {
        this.fabricationNumber = fabricationNumber;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public void setEngineTorque(float engineTorque) {
        this.engineTorque = engineTorque;
    }

    public void setAutodestructionDate(Date autodestructionDate) {
        this.autodestructionDate = autodestructionDate;
    }

    public void setHasDeathLaser(Boolean hasDeathLaser) {
        this.hasDeathLaser = hasDeathLaser;
    }

}
