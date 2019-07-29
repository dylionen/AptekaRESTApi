package apteka;

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
    private int id_user;

    @Column(name = "login_name")
    private String login_name;

    @Column(name = "password")
    private String password;

    @Column(name = "auth_token")
    private String auth_token;

    @Column(name = "created_date")
    private Date created_date;



    public User(String login_name, String password) {
        this.login_name = login_name;
        this.password = password;
        SecureRandom secureRandom = new SecureRandom();
        Base64.Encoder base64Encoder = Base64.getUrlEncoder();
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        this.auth_token = base64Encoder.encodeToString(randomBytes);
        this.created_date = new Date();
    }

    public User() {
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getLogin_name() {
        return login_name;
    }

    public void setLogin_name(String login_name) {
        this.login_name = login_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
