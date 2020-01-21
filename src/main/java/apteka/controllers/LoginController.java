package apteka.controllers;

import apteka.functionality.CodersClass;
import apteka.tables.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        String md5Password = CodersClass.MD5Code(password);

        List<User> users = session.createQuery("from User where loginName = '" + login + "'" +
                " and password = '" + md5Password + "'").getResultList();
        session.getTransaction().commit();
        if (users.size() == 1) {
            return ResponseEntity.ok(objectMapper.writeValueAsString(users));
        } else
            return ResponseEntity.ok(HttpStatus.BAD_REQUEST);
    }

    //Pobranie wszystkich userów z bazy

    /*
     @GetMapping("/users")
     public ResponseEntity getUsers() throws JsonProcessingException {
         Session session = sessionUserFactory.getCurrentSession();

         Transaction tx = session.beginTransaction();


         List<User> users = session.createQuery("from User").getResultList();
         session.getTransaction().commit();
         return ResponseEntity.ok(objectMapper.writeValueAsString(users));
     }
 */
    //utworzenie nowego usera
    @PostMapping("/users")
    public ResponseEntity createUser(@RequestBody User user) throws JsonProcessingException {
        Session session = sessionUserFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<UserType> userType = session.createQuery("from UserType where name = 'Pracownik'").getResultList();

        User newUser = new User(user.getLoginName(), user.getPassword(),
                userType.get(0), user.getName(), user.getSecondName(), user.getSurname(), user.getDateOfBirth());

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

    @GetMapping("checkCorrectionAuthToken/{authtoken}")
    public int checkCorrectionHashCode(@PathVariable String authtoken) {
        Session session = sessionUserFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<User> userList = session.createQuery("from User where authToken = '" + authtoken + "'").getResultList();
        session.getTransaction().commit();
        if (userList.size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }
}
