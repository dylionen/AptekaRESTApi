package apteka.tables;

import javax.persistence.*;
@Entity
@Table(name = "address_types")
public class AddressType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_addresstype", updatable = false, nullable = false)
    private int idAddressType;

    @Column(name = "value")
    private String value;

    public AddressType(String value) {
        this.value = value;
    }
    public AddressType(){}

    public int getIdAddressType() {
        return idAddressType;
    }

    public void setIdAddressType(int idAddressType) {
        this.idAddressType = idAddressType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
