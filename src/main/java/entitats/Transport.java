package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.sql.Date;
import java.io.Serializable;

/**
 * Classe abstracta per representar objectes de tipus Transport.
 *
 * @author Pablo Morante: Creació/Implementació
 */
@Entity
@Table(name = "transport")
public class Transport extends Pilotada implements TesteableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private int itemsCapacity;

    public Transport() {

    }

    public Transport(int itemsCapacity, boolean hasEjectoSeat, float shellCapacity, String corporation, float engineTorque, Date autodestructionDate, Boolean hasDeathLaser, Pilot pilot) {
        super(hasEjectoSeat, shellCapacity, corporation, engineTorque, autodestructionDate, hasDeathLaser, pilot);
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
        return "· Arxiu per l'AERONAU DE TRANSPORT número " + super.getFabricationNumber() + ". Propietat de la corporació " + super.getCorporation() + ", amb data programada per l'autodestrucció en " + super.getAutodestructionDate() + ". | Capacitat del Contenidor Doofenshmirtz Evil Inc.: " + itemsCapacity + " objectes, i una capacitat de defensa de l'escut de " + super.getShellCapacity() + " electrons.";

    }

}
