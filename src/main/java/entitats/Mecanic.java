package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import org.hibernate.annotations.ColumnDefault;

/**
 * Classe que defineix l'objecte de tipus Mecanic, que deriva de la Classe mare
 * 'Soldat' (RF01)
 *
 * @author pablomorante: Creació
 * @author Izan Jimenez: Implementació
 */
@Entity(name = "Mecanic")
@Table(name = "mecanic")
public class Mecanic extends Soldat implements TesteableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "speciality", nullable = false)
    private String speciality;

    @Basic(optional = false)
    @ColumnDefault("1.0")
    @Column(name = "repairSpeed", nullable = false)
    private Float repairSpeed;

    public Mecanic(String speciality, Float repairSpeed, int operatingNumber, String nickname, float healingSpeed, Date lastDrugTestDate, boolean isOtaku) {
        super(operatingNumber, nickname, healingSpeed, lastDrugTestDate, isOtaku);
        this.speciality = speciality;
        this.repairSpeed = repairSpeed;
    }

    public Mecanic() {
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Float getRepairSpeed() {
        return repairSpeed;
    }

    public void setRepairSpeed(Float repairSpeed) {
        this.repairSpeed = repairSpeed;
    }

    @Override
    public Integer getAtributIdentificador() {
        return super.getOperatingNumber();
    }

    @Override
    public String getAtributString() {
        return super.getNickname();
    }

    @Override
    public Float getAtributFloat() {
        return super.getHealingSpeed();
    }

    @Override
    public Date getAtributDate() {
        return super.getLastDrugTestDate();
    }

    @Override
    public Boolean getAtributBoolean() {
        return super.getIsOtaku();
    }

    @Override
    public void setAtributString(String s) {
        super.setNickname(s);
    }

    @Override
    public void setAtributFloat(Float f) {
        super.setHealingSpeed(f);
    }

    @Override
    public void setAtributDate(Date d) {
        super.setLastDrugTestDate(d);
    }

    @Override
    public void setAtributBoolean(Boolean b) {
        super.setIsOtaku(b);
    }
}
