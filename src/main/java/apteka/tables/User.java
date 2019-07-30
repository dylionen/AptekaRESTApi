package apteka.tables;

import lombok.*;

import javax.persistence.*;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
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
    private String password;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "id_usertype")
    private int idUserType;



    public User(String loginName, String password) {
        this.loginName = loginName;
        this.password = password;
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        this.authToken = base64Encoder.encodeToString(randomBytes);
        this.createdDate = new Date();
        this.idUserType = 1;
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

    public int getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(int idUserType) {
        this.idUserType = idUserType;
    }

    public User() {
    }


}
