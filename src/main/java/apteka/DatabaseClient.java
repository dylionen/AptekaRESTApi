package apteka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DatabaseClient {
    SessionFactory sessionFactory  =  new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .buildSessionFactory();

    ObjectMapper objectMapper = new ObjectMapper();


    @GetMapping("/users")
    public String getUsers() throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        List<User> userList = null;
        Transaction tx = session.beginTransaction();
        try {
            userList = session.createQuery("from User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objectMapper.writeValueAsString(userList);
    }

    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        User newUser = new User(user.getLogin_name(),user.getPassword());
        Transaction tx = session.beginTransaction();
        try {
            session.save(newUser);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                    "Error, please contact with administrator.",
                    HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(HttpStatus.OK);
    }



}
