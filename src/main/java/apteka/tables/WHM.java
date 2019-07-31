package apteka.tables;

import javax.persistence.*;

@Entity
@Table(name = "warehouse_movements")
public class WHM {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_warehouse_movement", updatable = false, nullable = false)
    private int idWh;

    @ManyToOne
    @JoinColumn(name = "id_user")
    private User idUser;

    @ManyToOne
    @JoinColumn(name = "id_type_warehouse_movement")
    private TypesWHM idTypeWHM;

    public WHM(User idUser, TypesWHM idTypeWHM) {
        this.idUser = idUser;
        this.idTypeWHM = idTypeWHM;
    }

    public WHM() {
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public TypesWHM getIdTypeWHM() {
        return idTypeWHM;
    }

    public void setIdTypeWHM(TypesWHM idTypeWHM) {
        this.idTypeWHM = idTypeWHM;
    }
}
