package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serializable;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.ColumnDefault;
import java.util.List;
import java.util.ArrayList;

/**
 * Classe que defineix l'objecte de tipus Mecanic, que deriva de la Classe mare
 * 'Soldat' (RF01)
 *
 * @author pablomorante: Creació
 * @author Izan Jimenez: Implementació
 */
@Entity(name = "Missio")
@Table(name = "missio")
public class Missio implements TesteableEntity, Serializable {

    @Id //defineix la clau primaria de la taula
    @GeneratedValue(strategy = GenerationType.IDENTITY) //defineix que el id és autogenerat per la base de dades i és de tipus AI (autoincrement)
    @Basic(optional = false) //camp no opcional (obligatòri) a nivell de Java
    @Column(name = "cosmicMissionCode", nullable = false) //camp no opcional (obligatòri) a nivell de SQL
    private int cosmicMissionCode;

    @Basic(optional = false)
    @Column(name = "targetName", nullable = false, unique = true)
    private String targetName;

    @ColumnDefault("10000.0")
    @Column(name = "missionBudget", nullable = false)
    private Float missionBudget;

    @Column(name = "limitDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date limitDate;

    @Basic
    @Column(name = "accomplished")
    private boolean accomplished;

    @ManyToMany(mappedBy = "missions", cascade = CascadeType.MERGE)
    @Column(length = 8)
    private List<Aeronau> aeronaus = new ArrayList<>();

    public Missio() {

    }

    public Missio(String targetName, Float missionBudget, Date limitDate, boolean accomplished) {
        this.targetName = targetName;
        this.missionBudget = missionBudget;
        this.limitDate = limitDate;
        this.accomplished = accomplished;
    }

    public List<Aeronau> getAeronaus() {
        return aeronaus;
    }

    public void setAeronaus(List<Aeronau> aeronaus) {
        this.aeronaus = aeronaus;
    }
    
    

    public int getCosmicMissionCode() {
        return cosmicMissionCode;
    }

    public void setCosmicMissionCode(int cosmicMissionCode) {
        this.cosmicMissionCode = cosmicMissionCode;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public float getMissionBudget() {
        return missionBudget;
    }

    public void setMissionBudget(float missionBudget) {
        this.missionBudget = missionBudget;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public boolean isAccomplished() {
        return accomplished;
    }

    public void setAccomplished(boolean accomplished) {
        this.accomplished = accomplished;
    }

    @Override
    public Integer getAtributIdentificador() {
        return cosmicMissionCode;
    }

    @Override
    public String getAtributString() {
        return targetName;
    }

    @Override
    public Float getAtributFloat() {
        return missionBudget;
    }

    @Override
    public Date getAtributDate() {
        return limitDate;
    }

    @Override
    public Boolean getAtributBoolean() {
        return accomplished;
    }

    @Override
    public void setAtributString(String s) {
        this.targetName = s;
    }

    @Override
    public void setAtributFloat(Float f) {
        this.missionBudget = f;
    }

    @Override
    public void setAtributDate(Date d) {
        this.limitDate = d;
    }

    @Override
    public void setAtributBoolean(Boolean b) {
        this.accomplished = b;
    }
    
    

    @PrePersist
    @PreUpdate
    private void validateAeronaus() {
        if (aeronaus.size() > 8) {
            throw new IllegalArgumentException("A Missio can have at most 8 Aeronau objects");
        }
    }

    @Override
    public String toString() {
        return "Missio{" + "cosmicMissionCode=" + cosmicMissionCode + ", targetName=" + targetName + ", missionBudget=" + missionBudget + ", limitDate=" + limitDate + ", accomplished=" + accomplished + ", aeronaus=" + aeronaus + '}';
    }

    
    
}
