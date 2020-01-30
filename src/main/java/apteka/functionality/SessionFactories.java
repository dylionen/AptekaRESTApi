package apteka.functionality;

import apteka.tables.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactories {
    public static SessionFactory NewFactory(){
        return new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Article.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(UserType.class)
                .addAnnotatedClass(Unit.class)
                .addAnnotatedClass(Localization.class)
                .addAnnotatedClass(Address.class)
                .addAnnotatedClass(AddressType.class)
                .addAnnotatedClass(Contact.class)
                .addAnnotatedClass(ContactType.class)
                .addAnnotatedClass(WHM.class)
                .addAnnotatedClass(WHMList.class)
                .addAnnotatedClass(VATTable.class)
                .addAnnotatedClass(TypesWHM.class)
                .addAnnotatedClass(Numerator.class)
                .addAnnotatedClass(ArticleReport.class)
                .buildSessionFactory();
    }
}
