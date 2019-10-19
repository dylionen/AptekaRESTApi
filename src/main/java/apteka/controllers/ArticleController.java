package apteka.controllers;

import apteka.functionality.AuthValidator;
import apteka.tables.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.imageio.ImageIO;
import javax.xml.ws.Response;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
public class ArticleController {
    SessionFactory sessionArticleFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Article.class)
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserType.class)
            .addAnnotatedClass(Unit.class)
            .addAnnotatedClass(Localization.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(Contact.class)
            .addAnnotatedClass(AddressType.class)
            .addAnnotatedClass(ContactType.class)
            .addAnnotatedClass(WHM.class)
            .addAnnotatedClass(WHMList.class)
            .addAnnotatedClass(VATTable.class)
            .addAnnotatedClass(TypesWHM.class)
            .buildSessionFactory();


    @Autowired
    ObjectMapper objectMapper;


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registy) {
                registy.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
            }
        };
    }

    //pobranie wszystkich artykułów
    @GetMapping("/articles")
    public String getAllArticles(@RequestHeader(value = "Localization") int localization,
                                 @RequestHeader(value = "Authorization") String authId) throws JsonProcessingException {
        Session session = sessionArticleFactory.getCurrentSession();
        List<Article> articles = null;

        Transaction tx = session.beginTransaction();
        Query sumQuery;
        double quantity;

        try {
            articles = session.createQuery("from Article where archived = false and foreignLocalization = " + localization).getResultList();


        } catch (Exception e) {
            tx.rollback();
            return e.getMessage();
        } finally {
            session.getTransaction().commit();
            session.close();
            //session.getTransaction().commit();
        }
        return objectMapper.writeValueAsString(articles);
    }

    //pobranie konkretnego artykułu
    @GetMapping("/articles/{id}")
    public String getActualArticle(@RequestHeader(value = "Authorization") String authId, @PathVariable(value = "id") int id) throws JsonProcessingException {
        Session session = sessionArticleFactory.getCurrentSession();
        List<Article> articles = null;

        Transaction tx = session.beginTransaction();

        try {
            articles = session.createQuery("from Article where archived = false and idArticle = " + id).getResultList();
        } catch (Exception e) {
            tx.rollback();
            return e.getMessage();
        } finally {
            session.getTransaction().commit();
            session.close();
        }
        return objectMapper.writeValueAsString(articles);
    }

    //Dodanie nowego artykułu
    @PostMapping("/articles")
    public String createArticle(@RequestHeader(value = "Authorization") String authId, @RequestBody Article newArticle,
                                @RequestHeader(value = "Localization") int localization) {
        Session session = sessionArticleFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();


        System.out.println("AuthToken: " + authId);

        List<User> userList = session.createQuery("from User").getResultList();
        if (userList.size() == 0) {
            return "Error, user not found.";
        }

        List<Unit> unitList = session.createQuery("from Unit where idUnit = " + newArticle.getForeignUnit()).getResultList();
        if (unitList.size() == 0) {
            return "Error, unit not found.";
        }

        List<Localization> localizationList = session.createQuery("from Localization where id = " + localization).getResultList();
        if (localizationList.size() == 0) {
            return "Error, localization not found.";
        }

        System.out.println(newArticle);
        Article article = new Article(newArticle.getName(), newArticle.getPrice(),
                unitList.get(0),
                newArticle.getForeignUnit(),
                userList.get(0),
                newArticle.getDescription(),
                0,localizationList.get(0));
        session.save(article);

        session.getTransaction().commit();
        session.close();
        return article.getIdArticle() + "";
    }

    //Aktualizacja artykułu
    @PutMapping("/articles/{id}")
    public ResponseEntity updateArticle(@PathVariable int id, @RequestHeader(value = "Authorization") String authId, @RequestBody Article newArticle) {
        Session session = sessionArticleFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        User user = AuthValidator.getAuth(authId);
        if (user == null) return new ResponseEntity<String>("Auth failed", HttpStatus.NOT_ACCEPTABLE);
        List<Article> articleList = session.createQuery("from Article where idArticle = " + id).getResultList();
        Article article = null;
        try {
            article = articleList.get(0);
            article.setDescription(newArticle.getDescription());
            article.setName(newArticle.getName());
            article.setPrice(newArticle.getPrice());
            session.update(article);

        } catch (Exception e) {
            e.getMessage();
        } finally {
            tx.commit();
            session.close();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //pobranie zdjęcia
    @GetMapping("/articles/logo/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Session session = sessionArticleFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();


        List<Article> unitArticle = session.createQuery("from Article where  idArticle = " + id).getResultList();
        tx.commit();
        session.close();
        byte[] image = unitArticle.get(0).getPhoto();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    //dodanie loga do konkretnego artykułu
    @PostMapping("/articles/addLogo/{id}")
    public ResponseEntity updateArticleLogo(@PathVariable int id, @RequestParam("avatar") MultipartFile file) {
        Session session = sessionArticleFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<Article> articlesList = session.createQuery("from Article where idArticle = " + id).getResultList();
        try {
            Article article = articlesList.get(0);
            System.out.println(file);
            article.setPhoto(file.getBytes());
            session.saveOrUpdate(article);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            tx.commit();
            session.close();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

    //archiwizacja danego artykułu
    @DeleteMapping("/articles/delete/{id}")
    public ResponseEntity archiveArticle(@PathVariable int id, @RequestHeader(value = "Authorization") String authId) {
        Session session = sessionArticleFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        User user = AuthValidator.getAuth(authId);

        if (user == null) return new ResponseEntity<String>("Auth failed", HttpStatus.NOT_ACCEPTABLE);
        List<Article> articlesList = session.createQuery("from Article where idArticle = " + id).getResultList();
        Article article = null;
        try {
            article = articlesList.get(0);
            article.setArchived(true);

        } catch (Exception e) {
            return new ResponseEntity<String>("Article delete failed", HttpStatus.NOT_ACCEPTABLE);
        } finally {
            tx.commit();
            session.close();
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
