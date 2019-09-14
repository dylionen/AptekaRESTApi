package apteka.tables;

import javax.persistence.*;
@Entity
@Table(name = "contact_types")
public class ContactType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contact_type", updatable = false, nullable = false)
    private int idContactType;

    @Column(name = "value")
    private String value;

    public ContactType(String value) {
        this.value = value;
    }

    public ContactType() {
    }

    public int getIdContactType() {
        return idContactType;
    }

    public void setIdContactType(int idContactType) {
        this.idContactType = idContactType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}


