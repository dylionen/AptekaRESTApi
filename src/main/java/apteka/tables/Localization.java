package apteka.tables;

import javax.persistence.*;
@Entity
@Table(name = "localizations")
public class Localization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localization", updatable = false, nullable = false)
    private int idLocalization;

    @ManyToOne
    @JoinColumn(name = "id_address")
    private Address idAddress;

    @ManyToOne
    @JoinColumn(name = "id_contact")
    private Contact idContact;

    @Column(name = "name")
    private String name;

    public Localization(Address idAddress, Contact idContact, String name) {
        this.idAddress = idAddress;
        this.idContact = idContact;
        this.name = name;
    }

    public Localization() {

    }


    public int getIdLocalization() {
        return idLocalization;
    }

    public void setIdLocalization(int idLocalization) {
        this.idLocalization = idLocalization;
    }

    public Address getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(Address idAddress) {
        this.idAddress = idAddress;
    }

    public Contact getIdContact() {
        return idContact;
    }

    public void setIdContact(Contact idContact) {
        this.idContact = idContact;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
