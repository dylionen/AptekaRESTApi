package apteka.tables;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "bufor")
    private boolean bufor;

    public WHM(User idUser, TypesWHM idTypeWHM) {
        this.idUser = idUser;
        this.idTypeWHM = idTypeWHM;
        this.createdDate = new Date();
        this.bufor = true;
    }

    public WHM() {
    }

    public User getIdUser() {
        return idUser;
    }

    public int getIdWh() {
        return idWh;
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

    public void setIdWh(int idWh) {
        this.idWh = idWh;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isBufor() {
        return bufor;
    }

    public void setBufor(boolean bufor) {
        this.bufor = bufor;
    }

}
