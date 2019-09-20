package apteka.controllers;

import apteka.functionality.AuthValidator;
import apteka.tables.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LocalizationController {
    SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(WHM.class)
            .addAnnotatedClass(WHMList.class)
            .addAnnotatedClass(UserType.class)
            .addAnnotatedClass(VATTable.class)
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(TypesWHM.class)
            .addAnnotatedClass(Article.class)
            .addAnnotatedClass(Unit.class)
            .addAnnotatedClass(Localization.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(AddressType.class)
            .addAnnotatedClass(Contact.class)
            .addAnnotatedClass(ContactType.class)
            .buildSessionFactory();

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/localizations")
    public String getLoginLocalizations(@RequestHeader(value = "Authorization") String authId) throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        //User user = AuthValidator.getAuth(authId);
        //if (user == null) return -1;

        List<Localization> userLocalizationsList = session.createQuery("from Localization").getResultList();


        session.getTransaction().commit();


        return objectMapper.writeValueAsString(userLocalizationsList);
    }


}
