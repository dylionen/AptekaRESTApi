package apteka.tables;

import javax.persistence.*;

@Entity
@Table(name = "units")
public class Unit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_unit", updatable = false, nullable = false)
    private int idUnit;

    @Column(name = "unit")
    private String unit;

    public int getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(int idUnit) {
        this.idUnit = idUnit;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Unit(){

    }
    public Unit(String unit) {
        this.unit = unit;
    }
}
