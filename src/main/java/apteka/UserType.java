package apteka;

import lombok.*;

import javax.persistence.*;



@Entity
@Table(name = "user_types")
@Getter
@Setter
@ToString
public class UserType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_type", updatable = false, nullable = false)
    private int idUserType;

    @Column(name = "name")
    private String name;

    public UserType(String name) {
        this.name = name;
    }

    public UserType() {
    }

    public int getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(int idUserType) {
        this.idUserType = idUserType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
