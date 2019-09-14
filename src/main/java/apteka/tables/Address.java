package apteka.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_address", updatable = false, nullable = false)
    private int idAddress;

    @Column(name = "street")
    private String street;

    @Column(name = "post_code")
    private String postCode;

    @ManyToOne
    @JoinColumn(name = "id_addresstype")
    private AddressType idAddressType;

    public Address(String street, String postCode, AddressType idAddressType) {
        this.street = street;
        this.postCode = postCode;
        this.idAddressType = idAddressType;
    }

    public Address() {
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public AddressType getIdAddressType() {
        return idAddressType;
    }

    public void setIdAddressType(AddressType idAddressType) {
        this.idAddressType = idAddressType;
    }
}
