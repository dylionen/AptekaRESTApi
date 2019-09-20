package apteka.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.*;
import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", updatable = false, nullable = false)
    private int idUser;

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "name")
    private String name;

    @Column(name = "second_name")
    private String secondName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;


    @Column(name = "created_date")
    @JsonIgnore
    private Date createdDate;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_usertype")
    private UserType idUserType;

    @Column(name = "id_usertype", insertable = false, updatable = false)
    @JsonProperty("id_usertype")
    private int foreignIdUserType;


    @ManyToMany
    @JoinTable(name = "localizations_users", joinColumns = {@JoinColumn(name = "id_user")}, inverseJoinColumns = {@JoinColumn(name = "id_localization")})
    private Collection<Localization> localization = new ArrayList<>();


    public User(String loginName, String password, UserType userType, String name, String secondName, String surname, Date dateOfBirth) {
        this.loginName = loginName;
        this.password = password;
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        this.authToken = base64Encoder.encodeToString(randomBytes);
        this.createdDate = new Date();
        this.idUserType = userType;
        this.name = name;
        this.secondName = secondName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;

    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setCreated_date(Date created_date) {
        this.createdDate = created_date;
    }

    public UserType getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(UserType idUserType) {
        this.idUserType = idUserType;
    }

    public User() {
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getForeignIdUserType() {
        return foreignIdUserType;
    }

    public void setForeignIdUserType(int foreignIdUserType) {
        this.foreignIdUserType = foreignIdUserType;
    }

    public Collection<Localization> getLocalization() {
        return localization;
    }

    public void setLocalization(Collection<Localization> localization) {
        this.localization = localization;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
