package apteka.tables;

import org.apache.tomcat.jni.Local;

import javax.persistence.*;


public class UserLocalizations {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id_localization")
    private int idLocalization;


    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id_user")
    private int idUser;

    public UserLocalizations() {
    }

    public UserLocalizations(int idUser) {
        this.idUser = idUser;
    }

    public int getIdLocalization() {
        return idLocalization;
    }

    public void setIdLocalization(int idLocalization) {
        this.idLocalization = idLocalization;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
