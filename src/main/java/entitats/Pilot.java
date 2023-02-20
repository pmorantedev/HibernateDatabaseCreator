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
 * Classe que defineix l'objecte de tipus Pilot, que deriva de la Classe
 * mare 'Soldat' (RF01)
 * 
 * @author pablomorante: Creació
 * @author Txell Llanas: Implementació
 */
@Entity(name = "Pilot")
@Table(name="pilot")
public class Pilot extends Soldat implements TesteableEntity, Serializable {

	private static final long serialVersionUID = 1L;
	
        @Basic(optional = false)
        @ColumnDefault("3.0")
	@Column(name = "max_gforce", nullable = false, precision = 1, scale = 2) // Estableix el mapeig entre l’atribut de l’entitat i el camp de la taula
    	private float maxGForce;

	public Pilot() {
	}
	public Pilot(int operatingNumber, String nickname, float healingSpeed, Date lastDrugTestDate, boolean isOtaku, Float maxGForce) {
		super(operatingNumber, nickname, healingSpeed, lastDrugTestDate, isOtaku);
		this.maxGForce = maxGForce;
	}

        public float getMaxGForce() {
            return maxGForce;
        }

        public void setMaxGForce(float maxGForce) {
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
}
