package apteka.tables;

import com.fasterxml.jackson.annotation.*;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcProperties;

import javax.persistence.*;
import javax.swing.text.View;


@Entity
@Table(name = "wh_list")
public class WHMList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_whlist", updatable = false, nullable = false)
    private int idWHList;

    @ManyToOne
    @JoinColumn(name = "article_id")
    @JsonProperty("idArticleValue")
    private Article idArticle;

    @Column(name = "article_id", insertable = false, updatable = false)
    @JsonProperty("idArticle")
    private int foreignIdArticle;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_warehouse_movement")
    private WHM idWHM;

    @Column(name = "id_warehouse_movement", insertable = false, updatable = false)
    @JsonProperty("id_warehouse_movement")
    private int foreignIdWHM;


    @Column(name = "value")
    private double value;

    @ManyToOne()
    @JsonIgnore
    @JoinColumn(name = "id_vat_table")
    private VATTable idVATTable;

    @Column(name = "id_vat_table", insertable = false, updatable = false)
    @JsonProperty("idVATTable")
    private int foreignIdVATTable;

    @Column(name = "price")
    private double price;

    @Column(name = "priceb")
    private double priceB;

    public WHMList(Article idArticle, WHM idWHM, double value, VATTable idVATTable, double price,double priceB) {
        this.idArticle = idArticle;
        this.idWHM = idWHM;
        this.value = value;
        this.idVATTable = idVATTable;
        this.price = price;
        this.priceB = priceB;
/*
        this.foreignIdArticle = idArticle.getIdArticle();
        this.foreignIdWHM = idWHM.getIdWh();
        this.foreignIdVATTable = idVATTable.getIdVat();*/
    }

    public int getForeignIdArticle() {
        return foreignIdArticle;
    }

    public void setForeignIdArticle(int foreignIdArticle) {
        this.foreignIdArticle = foreignIdArticle;
    }

    public int getForeignIdWHM() {
        return foreignIdWHM;
    }

    public void setForeignIdWHM(int foreignIdWHM) {
        this.foreignIdWHM = foreignIdWHM;
    }

    public int getForeignIdVATTable() {
        return foreignIdVATTable;
    }

    public void setForeignIdVATTable(int foreignIdVATTable) {
        this.foreignIdVATTable = foreignIdVATTable;
    }

    public WHMList(int foreignIdArticle, int foreignIdWHM, double value, int foreignIdVATTable, double price,double priceB) {
        this.foreignIdArticle = foreignIdArticle;
        this.foreignIdWHM = foreignIdWHM;
        this.value = value;
        this.foreignIdVATTable = foreignIdVATTable;
        this.price = price;
        this.priceB = priceB;
    }

    public WHMList() {
    }

    public int getIdWHList() {
        return idWHList;
    }

    public void setIdWHList(int idWHList) {
        this.idWHList = idWHList;
    }

    public Article getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Article idArticle) {
        this.idArticle = idArticle;
    }

    public WHM getIdWHM() {
        return idWHM;
    }

    public void setIdWHM(WHM idWHM) {
        this.idWHM = idWHM;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public VATTable getIdVATTable() {
        return idVATTable;
    }

    public void setIdVATTable(VATTable idVATTable) {
        this.idVATTable = idVATTable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceB() {
        return priceB;
    }

    public void setPriceB(double priceB) {
        this.priceB = priceB;
    }
}
