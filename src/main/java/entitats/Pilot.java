package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.sql.Date;
import org.hibernate.annotations.ColumnDefault;

/**
 * Classe que defineix l'objecte de tipus Pilot, que deriva de la Classe mare
 * 'Soldat' i les seves relacions amb la classe 'Aeronau Pilotada'. (RF01, RF04)
 *
 * @author pablomorante: Creació
 * @author Txell Llanas: Implementació
 */
@Entity(name = "Pilot")
@Table(name = "pilot")
public class Pilot extends Soldat implements TesteableEntity, Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @ColumnDefault("3.0")
    @Column(name = "max_gforce", nullable = false)
    private Float maxGForce;
    

    // (RF04) Cardinalitat Propietària i dependent amb la classe 'Pilotada'
    @OneToOne(mappedBy = "pilot", cascade = CascadeType.ALL)                    // cascade: enllaça les dades entre ambdues entitats (si eliminem la relació propietària, s'esborra les dades referenciades)
    private Pilotada pilotada;

    public Pilot() {
    }

    public Pilot(String nickname, Float healingSpeed, Date lastDrugTestDate, boolean isOtaku, Float maxGForce) {
        super(nickname, healingSpeed, lastDrugTestDate, isOtaku);
        this.maxGForce = maxGForce;
    }
    
    // Getters, Setters
    /**
     * (RF04) Mètode que obté la Clau Forània (FK) amb la que està relacionada.
     * Relació (0,1 : 0,1) bidireccional amb la classe 'Pilotada'. 
     * 
     * @param pilotada Instància que defineix la nau pilotada pel 'Pilot' actual.
     * @author Txell Llanas: Creació/Implementació
     */
    public Pilotada getPilotada() {
        return pilotada;
    }
    /**
     * (RF04) Mètode que estableix la Clau Forània (FK) amb la que està relacionada.
     * Relació (0,1 : 0,1) bidireccional amb la classe 'Pilotada'. 
     * És molt important actualitzar (SET) el costat propietari de la relació i
     * que conté la clau forània.
     * 
     * @param pilotada Instància que defineix la nau pilotada pel 'Pilot' actual.
     * @author Txell Llanas: Creació/Implementació
     */
    public void setPilotada(Pilotada pilotada) {    
        this.pilotada = pilotada;
    }
    

    public Float getMaxGForce() {
        return maxGForce;
    }

    public void setMaxGForce(Float maxGForce) {
        this.maxGForce = maxGForce;
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
        return "· Pilot File: OperatingID(" + super.getOperatingNumber() + ") for AMONGUS named " + super.getNickname() + ". Last drug test date: " + super.getLastDrugTestDate() + ". Born in aircraft number " + pilotada.getFabricationNumber() + " property of the corporation: " + pilotada.getCorporation() + ". Disability recorded (otaku): " + super.getIsOtaku();
    }
    
}
