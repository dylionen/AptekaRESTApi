package apteka.functionality;

import apteka.tables.Article;
import apteka.tables.Unit;
import apteka.tables.User;
import apteka.tables.UserType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


import java.util.List;

public class AuthValidator {
    public static User getAuth(String authKey) {
        SessionFactory sessionArticleFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Article.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserType.class)
                .addAnnotatedClass(Unit.class)
                .buildSessionFactory();
        Session session = sessionArticleFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<User> userList = session.createQuery("from User where authToken = '" + authKey + "'").getResultList();
        session.getTransaction().commit();
        if (userList.size() != 0) {
            return userList.get(0);
        } else
            return null;
    }
}
