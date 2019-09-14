package apteka.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_contact", updatable = false, nullable = false)
    private int idContact;

    @ManyToOne
    @JoinColumn(name = "id_contact_Type")
    private ContactType idContactType;

    @Column(name = "value")
    private String value;

    public Contact(ContactType idContactType, String value) {
        this.idContactType = idContactType;
        this.value = value;
    }

    public Contact() {
    }

    public int getIdContact() {
        return idContact;
    }

    public void setIdContact(int idContact) {
        this.idContact = idContact;
    }

    public ContactType getIdContactType() {
        return idContactType;
    }

    public void setIdContactType(ContactType idContactType) {
        this.idContactType = idContactType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
