package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.sql.Date;

/**
 * Classe que defineix l'objecte de tipus Dron
 *
 * @author Pablo Morante: Creació
 * @author Víctor García: Implementació
 */
@Entity
@Table
@MappedSuperclass
public class Dron extends Autonoma implements TesteableEntity {

    @Column(nullable = false)
    private float meltingTemperature;

    public Dron(float meltingTemperature, boolean invisibilityPower, int batteryAutonomy, int fabricationNumber, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        super(invisibilityPower, batteryAutonomy, fabricationNumber, corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.meltingTemperature = meltingTemperature;
    }
    
    public float getMeltingTemperature() {
        return meltingTemperature;
    }

    public void setMeltingTemperature(float meltingTemperature) {
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
}
