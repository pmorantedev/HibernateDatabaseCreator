package entitats;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.sql.Date;
import org.hibernate.annotations.ColumnDefault;

/**
 * Classe abstracta per representar objectes de tipus Soldat.
 * Hereten d'ella: Mecànic i Pilot. (RF01, RF02)
 * 
 * @author pablomorante: Creació
 * @author Txell Llanas: Implementació
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="soldat")
public abstract class Soldat implements Serializable {

    private static final long serialVersionUID = 1L;                            // Permet detectar quan els atributs de l'objecte canvien

    @Id                                                                         // Defineix la clau primària de la taula
    @GeneratedValue(strategy = GenerationType.IDENTITY)                         // Defineix que el id és autogenerat per la base de dades i és de tipus AI (autoincrement)
    @Basic(optional = false)                                                    // Camp no opcional (obligatòri) a nivell de Java
    @Column(name="operating_number", nullable = false)                          // Camp no opcional (obligatòri) a nivell de SQL
    private int operatingNumber;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String nickname;
    
    @ColumnDefault("5.0")
    @Column(name="healing_speed", nullable = false)
    private Float healingSpeed;
    
    @Column(name="last_drug_test_date", nullable = true)
    @Temporal(TemporalType.DATE)
    private Date lastDrugTestDate;
    
    @Basic
    @Column(name="is_otaku")
    private boolean isOtaku;

    public Soldat() {
    }

    public Soldat(String nickname, Float healingSpeed, Date lastDrugTestDate, boolean isOtaku) {
        this.nickname = nickname;
        this.healingSpeed = healingSpeed;
        this.lastDrugTestDate = lastDrugTestDate;
        this.isOtaku = isOtaku;
    }

    public int getOperatingNumber() {
        return operatingNumber;
    }

    public void setOperatingNumber(int operatingNumber) {
        this.operatingNumber = operatingNumber;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Float getHealingSpeed() {
        return healingSpeed;
    }

    public void setHealingSpeed(Float healingSpeed) {
        this.healingSpeed = healingSpeed;
    }

    public Date getLastDrugTestDate() {
        return lastDrugTestDate;
    }

    public void setLastDrugTestDate(Date lastDrugTestDate) {
        this.lastDrugTestDate = lastDrugTestDate;
    }

    public boolean getIsOtaku() {
        return isOtaku;
    }

    public void setIsOtaku(boolean isOtaku) {
        this.isOtaku = isOtaku;
    }

}
