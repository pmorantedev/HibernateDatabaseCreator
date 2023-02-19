package entitats;

import interficies.TesteableEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
@Entity(name = "Missio")
@Table(name = "missio")
public class Missio implements TesteableEntity, Serializable {

    @Id //defineix la clau primaria de la taula
    @GeneratedValue(strategy = GenerationType.IDENTITY) //defineix que el id és autogenerat per la base de dades i és de tipus AI (autoincrement)
    @Basic(optional = false) //camp no opcional (obligatòri) a nivell de Java
    @Column(name = "cosmicMissionCode", nullable = false) //camp no opcional (obligatòri) a nivell de SQL
    private int cosmicMissionCode;

    @Basic(optional = false)
    @Column(name = "targetName", length = 10, nullable = false, unique = true)
    private String targetName;

    @ColumnDefault("10000.0")
    @Column(name = "missionBudget", nullable = false)
    private float missionBudget;

    @Column(name = "limitDate", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date limitDate;

    @Basic
    @Column(name = "accomplished")
    private boolean accomplished;

    public Missio(int cosmicMissionCode, String targetName, float missionBudget, Date limitDate, boolean accomplished) {
        this.cosmicMissionCode = cosmicMissionCode;
        this.targetName = targetName;
        this.missionBudget = missionBudget;
        this.limitDate = limitDate;
        this.accomplished = accomplished;
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
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getAtributString() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Float getAtributFloat() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Date getAtributDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Boolean getAtributBoolean() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAtributString(String s) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAtributFloat(Float f) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAtributDate(Date d) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setAtributBoolean(Boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
