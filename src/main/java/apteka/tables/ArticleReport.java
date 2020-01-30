package apteka.tables;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "articles_reports")
public class ArticleReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", updatable = false, nullable = false)
    private int idReport;

    @ManyToOne
    @JoinColumn(name = "article_id")
    private Article idArticle;

    @ManyToOne
    @JoinColumn(name = "localization_id")
    private Localization idLocalization;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User idUser;

    @Column(name = "document")
    private byte[] document;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "report_for_day")
    private Date reportForDay;

    public ArticleReport(Article idArticle, Localization idLocalization, User idUser, byte[] document, Date reportForDay) {
        this.idArticle = idArticle;
        this.idLocalization = idLocalization;
        this.idUser = idUser;
        this.document = document;
        this.createdDate = new Date();
        this.reportForDay = reportForDay;
    }

    public Date getReportForDay() {
        return reportForDay;
    }

    public void setReportForDay(Date reportForDay) {
        this.reportForDay = reportForDay;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public ArticleReport() {

    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public int getIdReport() {
        return idReport;
    }

    public void setIdReport(int idReport) {
        this.idReport = idReport;
    }

    public Article getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(Article idArticle) {
        this.idArticle = idArticle;
    }

    public Localization getIdLocalization() {
        return idLocalization;
    }

    public void setIdLocalization(Localization idLocalization) {
        this.idLocalization = idLocalization;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }
}
