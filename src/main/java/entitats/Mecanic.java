package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "pilotada_id")
    private Pilotada pilotada;

    /**
     * *
     * Constructor de la classe Mecanic
     *
     * @param speciality String
     * @param repairSpeed Float
     * @param pilotada Pilotada
     * @param nickname String
     * @param healingSpeed FLoat
     * @param lastDrugTestDate Date
     * @param isOtaku Boolean
     */
    public Mecanic(String speciality, Float repairSpeed, Pilotada pilotada, String nickname, Float healingSpeed, Date lastDrugTestDate, boolean isOtaku) {
        super(nickname, healingSpeed, lastDrugTestDate, isOtaku);
        this.speciality = speciality;
        this.repairSpeed = repairSpeed;
        this.pilotada = pilotada;
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

    /**
     * *
     * (RF05) Mètode que obté la Clau Forània (FK) amb la que està relacionada.
     * Relació (0,1 : 0,2) bidireccional amb la classe 'Pilotada'.
     *
     * @return Pilotada
     */
    public Pilotada getPilotada() {
        return pilotada;
    }

    /**
     * (RF05) Mètode que estableix la Clau Forània (FK) amb la que està
     * relacionada. Relació (0,1 : 0,2) bidireccional amb la classe 'Pilotada'.
     *
     * @param pilotada Pilotada
     */
    public void setPilotada(Pilotada pilotada) {
        this.pilotada = pilotada;
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

    @Override
    public String toString() {
        return "Mecanic{"
                + "speciality = " + speciality
                + ", repairSpeed = " + repairSpeed
                + ", nickname = " + super.getNickname()
                + ", healingSpeed = " + super.getHealingSpeed()
                + ", lastDrugTestDate = " + super.getLastDrugTestDate()
                + ", isOtaku = " + super.getIsOtaku()
                + '}';
    }

}
