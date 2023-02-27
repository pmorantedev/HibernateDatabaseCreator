package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 * Classe que defineix l'objecte de tipus Dron
 *
 * @author Pablo Morante: Creació
 * @author Víctor García: Implementació
 */
@Entity
@Table(name = "dron")
public class Dron extends Autonoma implements TesteableEntity, Serializable {

    @Column(nullable = false)
    private Float meltingTemperature;

    public Dron(){
        
    }
    
    public Dron(Float meltingTemperature, boolean invisibilityPower, int batteryAutonomy, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        super(invisibilityPower, batteryAutonomy, corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.meltingTemperature = meltingTemperature;
    }
    
    public Float getMeltingTemperature() {
        return meltingTemperature;
    }

    public void setMeltingTemperature(Float meltingTemperature) {
        this.meltingTemperature = meltingTemperature;
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
        return super.getEngineTorque();
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

    @Override
    public String toString() {
        return "Dron{" +
                "meltingTemperature = " + meltingTemperature +
                ", invisibilityPower = " + super.isInvisibilityPower() +
                ", batteryAutonomy = " + super.getBatteryAutonomy() +
                ", corporation = " + super.getCorporation() +
                ", engineTorque = " + super.getEngineTorque() +
                ", autodestructionDate = " + super.getAutodestructionDate() +
                ", hasDeathLaser = " + super.getHasDeathLaser() +
                '}';
    }
    
    
}
