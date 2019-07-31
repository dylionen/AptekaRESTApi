package apteka.tables;

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

    @Column(name = "unit")
    private String unit;

    @ManyToOne
    @JoinColumn(name="id_user")
    private User idUser;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    private Date createdDate;

    public Article(){

    }

    public Article(String name, double price, String unit, User idUser, String description, Date createdDate) {
        this.name = name;
        this.price = price;
        this.unit = unit;
        this.idUser = idUser;
        this.description = description;
        this.createdDate = createdDate;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
}
