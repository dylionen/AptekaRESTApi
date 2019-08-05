package apteka.controllers;

import apteka.tables.Article;
import apteka.tables.Unit;
import apteka.tables.User;
import apteka.tables.UserType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
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
            .buildSessionFactory();
    ObjectMapper objectMapper = new ObjectMapper();


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registy) {
                registy.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD");
            }
        };
    }

    @GetMapping("/articles")
    public String getAllArticles() throws JsonProcessingException {
        Session session = sessionArticleFactory.getCurrentSession();
        List<Article> articles = null;

        Transaction tx = session.beginTransaction();

        try {
            articles = session.createQuery("from Article").getResultList();

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

    @PostMapping("/articles")
    public String createArticle(@RequestHeader(value = "Authorization") String authId, @RequestBody Article newArticle) {
        Session session = sessionArticleFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<User> userList = session.createQuery("from User where authToken = '" + authId + "'").getResultList();
        if (userList.size() == 0) {
            return "Error, user not found.";
        }

        List<Unit> unitList = session.createQuery("from Unit where idUnit = " + newArticle.getForeignUnit()).getResultList();

        System.out.println(newArticle);
        Article article = new Article(newArticle.getName(), newArticle.getPrice(),
                unitList.get(0),
                newArticle.getForeignUnit(),
                userList.get(0),
                newArticle.getDescription(),
                new Date());
        session.save(article);

        session.getTransaction().commit();
        session.close();
        return article.getIdArticle() + "";
    }

    @GetMapping("/articles/logo/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Session session = sessionArticleFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<Article> unitArticle = session.createQuery("from Article where idArticle = " + id).getResultList();
        tx.commit();
        session.close();
        byte[] image = unitArticle.get(0).getPhoto();
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
    }

    @PostMapping("/articles/addLogo/{id}")
    public ResponseEntity updateArticle(@PathVariable int id, @RequestParam("avatar") MultipartFile file) {
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


}
