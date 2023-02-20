package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Table;
import java.sql.Date;
import java.io.Serializable;

/**
 *
 * @author pablomorante
 */
@Entity
@MappedSuperclass
@Table
public class Transport extends Pilotada implements TesteableEntity, Serializable {

    @Column(nullable = false)
    private int itemsCapacity;

    public Transport(int itemsCapacity, boolean hasEjectoSeat, float shellCapacity, int fabricationNumber, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
        super(hasEjectoSeat, shellCapacity, fabricationNumber, corporation, engineTorque, autodestructionDate, hasDeathLaser);
        this.itemsCapacity = itemsCapacity;
    }

    public int getItemsCapacity() {
        return itemsCapacity;
    }

    public void setItemsCapacity(int itemsCapacity) {
        this.itemsCapacity = itemsCapacity;
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