package apteka.tables;

import javax.persistence.*;

@Entity
@Table(name = "wh_list")
public class WHMList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_whlist", updatable = false, nullable = false)
    private int idWHList;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article idArticle;

    @ManyToOne
    @JoinColumn(name = "id_warehouse_movement")
    private WHM idWHM;

    @Column(name = "value")
    private double value;

    @ManyToOne
    @JoinColumn(name = "id_VAT_table")
    private VATTable idVATTable;

    @Column(name = "price")
    private double price;

    public WHMList(Article idArticle, WHM idWHM, double value, VATTable idVATTable, double price) {
        this.idArticle = idArticle;
        this.idWHM = idWHM;
        this.value = value;
        this.idVATTable = idVATTable;
        this.price = price;
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
}
