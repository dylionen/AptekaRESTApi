package apteka.tables;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@ToString

@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article", updatable = false, nullable = false)
    private int idArticle;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_unit")
    private Unit idUnit;

    @Column(name = "id_unit", insertable = false, updatable = false)
    @JsonProperty("id_unit")
    private int foreignUnit;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_user")
    private User idUser;

    @Column(name = "id_user", insertable = false, updatable = false)
    @JsonProperty("idUser")
    private int foreignUser;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "archived")
    private boolean archived;

    @Column(name = "quantity")
    private int quantity;

    public Article() {

    }

    public Article(String name, double price, Unit idUnit, int foreignUnit, User idUser, String description, int quantity) {
        this.name = name;
        this.price = price;
        this.idUnit = idUnit;
        this.foreignUnit = foreignUnit;
        this.idUser = idUser;
        this.description = description;
        this.createdDate = new Date();
        this.archived = false;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getForeignUnit() {
        return foreignUnit;
    }

    public void setForeignUnit(int foreignUnit) {
        this.foreignUnit = foreignUnit;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getForeignUser() {
        return foreignUser;
    }

    public void setForeignUser(int foreignUser) {
        this.foreignUser = foreignUser;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Unit getIdUnit() {
        return idUnit;
    }

    public void setIdUnit(Unit idUnit) {
        this.idUnit = idUnit;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }


}
