package apteka.controllers;

import apteka.tables.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Local;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class LoginController {


    SessionFactory sessionUserFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(UserType.class)
            .addAnnotatedClass(Localization.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(AddressType.class)
            .addAnnotatedClass(Contact.class)
            .addAnnotatedClass(ContactType.class)
            .buildSessionFactory();


    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/login/{login}/{password}")
    public ResponseEntity login(@PathVariable String login, @PathVariable String password) throws JsonProcessingException {
        Session session = sessionUserFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<User> users = session.createQuery("from User where loginName = '" + login + "'" +
                " and password = '" + password + "'").getResultList();
        session.getTransaction().commit();
        if (users.size() == 1) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(users));
        } else
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    //Pobranie wszystkich userów z bazy
    @GetMapping("/users")
    public ResponseEntity getUsers() throws JsonProcessingException {
        Session session = sessionUserFactory.getCurrentSession();

        Transaction tx = session.beginTransaction();


        List<User> users = session.createQuery("from User").getResultList();
        session.getTransaction().commit();
        return ResponseEntity.ok(objectMapper.writeValueAsString(users));


        /*
        Session session = sessionUserFactory.getCurrentSession();
        List<User> userList = null;
        Transaction tx = session.beginTransaction();
        try {
            userList = session.createQuery("from User").list();
            session.getTransaction().commit();
            for (User s:userList){
                System.out.println(s.getCreatedDate());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objectMapper.writeValueAsString(userList);
        */
    }

    //utworzenie nowego usera
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) throws JsonProcessingException {
        Session session = sessionUserFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<UserType> userType = session.createQuery("from UserType where name = 'Pracownik'").getResultList();

        User newUser = new User(user.getLoginName(), user.getPassword(), userType.get(0));

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

    //Pobranie typów użytkowników
    @GetMapping("/usertypes")
    public String getUserTypes() throws JsonProcessingException {
        Session session = sessionUserFactory.getCurrentSession();
        List<UserType> userTypesList = null;
        Transaction tx = session.beginTransaction();
        try {
            userTypesList = session.createQuery("from UserType").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return objectMapper.writeValueAsString(userTypesList);
    }


    //Przypisanie użytkownika do lokalizacji
    @PostMapping("/assignusertoloc/{idUser}/{idLocalization}")
    public ResponseEntity assignUserToLocalization(@PathVariable String idUser, @PathVariable String idLocalization) throws JsonProcessingException {
        Session session = sessionUserFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<User> userList = session.createQuery("from User where idUser = " + idUser).getResultList();
        List<Localization> localizationList = session.createQuery("from Localization where idLocalization = " + idLocalization).getResultList();

        if (userList.size() != 1) return new ResponseEntity("Brak takiego uzytkownika", HttpStatus.CONFLICT);
        if (localizationList.size() != 1) return new ResponseEntity("Brak takiej lokalizacji", HttpStatus.CONFLICT);

        User user = userList.get(0);
        Localization localization = localizationList.get(0);

        user.getLocalization().add(localization);

        try {
            session.saveOrUpdate(user);
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
