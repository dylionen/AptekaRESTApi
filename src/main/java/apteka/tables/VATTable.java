package apteka.tables;

import javax.persistence.*;

@Entity
@Table(name = "vat_table")
public class VATTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vat", updatable = false, nullable = false)
    private int idVat;

    @Column(name = "name")
    private String name;

    @Column(name = "value")
    private double value;

    public VATTable() {
    }

    public VATTable(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public int getIdVat() {
        return idVat;
    }

    public void setIdVat(int idVat) {
        this.idVat = idVat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
