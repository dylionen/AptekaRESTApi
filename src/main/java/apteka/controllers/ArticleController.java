package apteka.controllers;

import apteka.tables.Article;
import apteka.tables.User;
import apteka.tables.UserType;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {
    SessionFactory sessionArticleFactory  =  new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Article.class).addAnnotatedClass(User.class).addAnnotatedClass(UserType.class)
            .buildSessionFactory();
    ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/articles")
    public String getAllArticles() throws JsonProcessingException {
        Session session = sessionArticleFactory.getCurrentSession();
        List<Article> articles = null;

        Transaction tx = session.beginTransaction();

        try{
            articles = session.createQuery("from Article").getResultList();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            return e.getMessage();
        } finally {
            //session.getTransaction().commit();
        }
        return objectMapper.writeValueAsString(articles);
    }


}
