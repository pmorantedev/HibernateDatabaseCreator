package entitats;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author pablomorante
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "aeronau")
public abstract class Aeronau {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private int fabricationNumber;
    @Column(nullable = false)
    private String corporation;
    @Column(nullable = false)
    private Float engineTorque;
    @Column(nullable = true)
    @Temporal(TemporalType.DATE)
    private Date autodestructionDate;
    @Column(nullable = false)
    private Boolean hasDeathLaser;
//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(
//            name = "Aeronau_Missio",
//            joinColumns = {
//                @JoinColumn(name = "aeronauFabricationNumber")},
//            inverseJoinColumns = {
//                @JoinColumn(name = "cosmicMissionCode")}
//    )
    @ManyToMany(mappedBy = "aeronaus", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    List<Missio> missions = new ArrayList<>();

    public Aeronau() {

    }

    public Aeronau(String corporation, Float engineTorque, Date autodestructionDate, Boolean hasDeathLaser) {
//        this.fabricationNumber = fabricationNumber;
        this.corporation = corporation;
        this.engineTorque = engineTorque;
        this.autodestructionDate = autodestructionDate;
        this.hasDeathLaser = hasDeathLaser;
    }

    public int getFabricationNumber() {
        return fabricationNumber;
    }

    public String getCorporation() {
        return corporation;
    }

    public Float getEngineTorque() {
        return engineTorque;
    }

    public Date getAutodestructionDate() {
        return autodestructionDate;
    }

    public Boolean getHasDeathLaser() {
        return hasDeathLaser;
    }

    public void setFabricationNumber(int fabricationNumber) {
        this.fabricationNumber = fabricationNumber;
    }

    public void setCorporation(String corporation) {
        this.corporation = corporation;
    }

    public void setEngineTorque(Float engineTorque) {
        this.engineTorque = engineTorque;
    }

    public void setAutodestructionDate(Date autodestructionDate) {
        this.autodestructionDate = autodestructionDate;
    }

    public void setHasDeathLaser(Boolean hasDeathLaser) {
        this.hasDeathLaser = hasDeathLaser;
    }

    public List<Missio> getMissions() {
        return missions;
    }

    public void setMissions(List<Missio> missions) {
        if (missions.size() > 2) {
            throw new IllegalArgumentException("Una aeronau pot tenir com a màxim 2 missions");
        } else {
            this.missions = missions;
        }
    }

}
