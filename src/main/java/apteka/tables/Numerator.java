package apteka.tables;

import javax.persistence.*;

@Entity
@Table(name = "numerator")
public class Numerator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_numerator", updatable = false, nullable = false)
    private int idNumerator;

    @Column(name = "value")
    private int value;

    @Column(name = "description")
    private String description;

    @Column(name = "isactive")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "id_type_warehouse_movement")
    private TypesWHM idWHMType;

    @Column(name = "last_use_month")
    private int lastUseMonth;

    @Column(name = "last_use_year")
    private int lastUseYear;

    @ManyToOne
    @JoinColumn(name = "id_localization")
    private Localization idLocalization;

    public Numerator(int value, String description, boolean isActive, TypesWHM idWHMType,
                     int lastUseMonth, int lastUseYear, Localization idLocalization) {
        this.value = value;
        this.description = description;
        this.isActive = isActive;
        this.idWHMType = idWHMType;
        this.lastUseMonth = lastUseMonth;
        this.lastUseYear = lastUseYear;
        this.idLocalization = idLocalization;
    }

    public Localization getIdLocalization() {
        return idLocalization;
    }

    public void setIdLocalization(Localization idLocalization) {
        this.idLocalization = idLocalization;
    }

    public Numerator() {
    }

    public int getIdNumerator() {
        return idNumerator;
    }

    public void setIdNumerator(int idNumerator) {
        this.idNumerator = idNumerator;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public TypesWHM getIdWHMType() {
        return idWHMType;
    }

    public void setIdWHMType(TypesWHM idWHMType) {
        this.idWHMType = idWHMType;
    }

    public int getLastUseMonth() {
        return lastUseMonth;
    }

    public void setLastUseMonth(int lastUseMonth) {
        this.lastUseMonth = lastUseMonth;
    }

    public int getLastUseYear() {
        return lastUseYear;
    }

    public void setLastUseYear(int lastUseYear) {
        this.lastUseYear = lastUseYear;
    }

    @Override
    public String toString() {
        return "Numerator{" +
                "idNumerator=" + idNumerator +
                ", value=" + value +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                ", idWHMType=" + idWHMType +
                ", lastUseMonth=" + lastUseMonth +
                ", lastUseYear=" + lastUseYear +
                ", idLocalization=" + idLocalization +
                '}';
    }
}
