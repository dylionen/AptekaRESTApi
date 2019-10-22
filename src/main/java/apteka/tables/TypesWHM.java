package apteka.tables;

import javax.persistence.*;

@Entity
@Table(name = "types_warehouse_movements")
public class TypesWHM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_warehouse_movement", updatable = false, nullable = false)
    private int id_whmtype;

    @Column(name = "value")
    private String value;

    @Column(name = "akronim")
    private String akronim;

    public TypesWHM() {
    }

    public TypesWHM(String value, String akronim) {
        this.value = value;
        this.akronim = akronim;
    }

    public String getAkronim() {
        return akronim;
    }

    public void setAkronim(String akronim) {
        this.akronim = akronim;
    }

    public int getId_whmtype() {
        return id_whmtype;
    }

    public void setId_whmtype(int id_whmtype) {
        this.id_whmtype = id_whmtype;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
