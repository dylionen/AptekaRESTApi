package apteka.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonIgnore
    @JoinColumn(name = "id_user")
    private User idUser;

    @Column(name = "id_user", insertable = false, updatable = false)
    @JsonProperty("id_user")
    private int foreignIdUser;

    @ManyToOne
    @JoinColumn(name = "id_type_warehouse_movement")
    private TypesWHM idTypeWHM;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "bufor")
    private boolean bufor;

    @Column(name = "price")
    private Double price;

    @Column(name = "priceb")
    private Double priceB;

    @Column(name = "foreign_name")
    private String foreignName;

    @ManyToOne
    @JoinColumn(name = "id_localization")
    private Localization idLocalization;

    public WHM(User idUser, TypesWHM idTypeWHM, Double price, Boolean bufor, Double priceB, String foreignName, Localization idLocalization) {
        this.idUser = idUser;
        this.idTypeWHM = idTypeWHM;
        this.idLocalization = idLocalization;
        this.createdDate = new Date();
        this.bufor = bufor;
        this.price = price;
        this.priceB = priceB;
        this.foreignName = foreignName;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPriceB() {
        return priceB;
    }

    public void setPriceB(Double priceB) {
        this.priceB = priceB;
    }

    public String getForeignName() {
        return foreignName;
    }

    public void setForeignName(String foreignName) {
        this.foreignName = foreignName;
    }

    public int getForeignIdUser() {
        return foreignIdUser;
    }

    public void setForeignIdUser(int foreignIdUser) {
        this.foreignIdUser = foreignIdUser;
    }

    public Localization getIdLocalization() {
        return idLocalization;
    }

    public void setIdLocalization(Localization idLocalization) {
        this.idLocalization = idLocalization;
    }

}
