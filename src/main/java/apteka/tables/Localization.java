package apteka.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Column(name = "akronim")
    private String akronim;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "localizations_users", joinColumns = {@JoinColumn(name = "id_localization")}, inverseJoinColumns = {@JoinColumn(name = "id_user")})
    //@JoinTable(name = "user_localizations", joinColumns = {@JoinColumn(name = "id_localization")}, inverseJoinColumns = {@JoinColumn(name = "id_user")})
    private List<User> user = new ArrayList<>();


    public Localization(Address idAddress, Contact idContact, String name, String akronim) {
        this.idAddress = idAddress;
        this.idContact = idContact;
        this.name = name;
        this.akronim = akronim;
    }

    public Localization() {

    }

    public String getAkronim() {
        return akronim;
    }

    public void setAkronim(String akronim) {
        this.akronim = akronim;
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

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }
    /*
    public void addUser(User user) {
        this.users.add(user);
        user.getLocalizations().add(this);
    }

    public void removeUser(User user) {
        this.users.remove(user);
        user.getLocalizations().remove(this);
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }*/
}
