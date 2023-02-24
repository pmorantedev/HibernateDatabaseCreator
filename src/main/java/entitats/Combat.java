package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author pablomorante
 */
@Entity
@Table(name = "combat")
public class Combat extends Pilotada implements TesteableEntity, Serializable {
    
    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private boolean nuclearPower;
    @Column(nullable = false)
    private int totalAmmunition;

    public Combat(){
        
    }
    
    public Combat(boolean nuclearPower, int totalAmmunition, boolean hasEjectoSeat, float shellCapacity, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser, Pilot pilot) {
        super(hasEjectoSeat, shellCapacity, corporation, engineTorque, autodestructionDate, hasDeathLaser, pilot);
        this.nuclearPower = nuclearPower;
        this.totalAmmunition = totalAmmunition;
    }

    public boolean getNuclearPower() {
        return nuclearPower;
    }

    public int getTotalAmmunition() {
        return totalAmmunition;
    }

    public void setNuclearPower(boolean nuclearPower) {
        this.nuclearPower = nuclearPower;
    }

    public void setTotalAmmunition(int totalAmmunition) {
        this.totalAmmunition = totalAmmunition;
    }

    @Override
    public Integer getAtributIdentificador() {
        return super.getFabricationNumber();
    }

    @Override
    public String getAtributString() {
        return super.getCorporation();
    }

    @Override
    public Float getAtributFloat() {
        return super.getShellCapacity();
    }

    @Override
    public Date getAtributDate() {
        return super.getAutodestructionDate();
    }

    @Override
    public Boolean getAtributBoolean() {
        return super.getHasDeathLaser();
    }

    @Override
    public void setAtributString(String s) {
        super.setCorporation(s);
    }

    @Override
    public void setAtributFloat(Float f) {
        super.setEngineTorque(f);
    }

    @Override
    public void setAtributDate(Date d) {
        super.setAutodestructionDate(d);
    }

    @Override
    public void setAtributBoolean(Boolean b) {
        super.setHasDeathLaser(b);
    }

}
