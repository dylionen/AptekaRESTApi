package apteka.functionality;

import apteka.tables.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.w3c.dom.DocumentType;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class NumeratorFunctions {

    public static String getNumeratorString(Date date, int idLocalization, int documentType) {
        SessionFactory sessionFactory = SessionFactories.NewFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        String returnValue;
        Numerator numerator = null;
        numerator = getCurrentOrCreateNumerator(session, idLocalization, documentType);


        if (numerator == null) {
            return "error";
        }

        validateNumerator(numerator, date);

        returnValue = String.format("%s/%d/%d/%d/%s", numerator.getIdWHMType().getAkronim(),
                numerator.getValue(), numerator.getLastUseMonth() + 1, numerator.getLastUseYear(), numerator.getIdLocalization().getAkronim());

        incrementCounter(numerator);
        session.getTransaction().commit();
        session.close();

        return returnValue;
    }

    private static Numerator getCurrentOrCreateNumerator(Session session, int idLocalization, int documentType) {
        Numerator numerator;
        numerator = getCurrentNumerator(session, idLocalization, documentType);

        if (numerator == null) {
            createNewNumerator(idLocalization, documentType);
            numerator = getCurrentNumerator(session, idLocalization, documentType);
        }
        return numerator;
    }

    private static Numerator getCurrentNumerator(Session session, int idLocalization, int documentType) {
        List<Numerator> numeratorList = session.createQuery("from Numerator num where num.idLocalization.idLocalization = "
                + idLocalization + " and num.idWHMType.id_whmtype = " + documentType).getResultList();
        if (numeratorList.size() == 0)
            return null;
        else
            return numeratorList.get(0);
    }


    private static void createNewNumerator(int idLocalization, int documentType) {
        SessionFactory sessionFactory = SessionFactories.NewFactory();
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<Localization> localizationList = session.createQuery("from Localization where idLocalization = "
                + idLocalization).getResultList();
        Localization localization = localizationList.get(0);

        List<TypesWHM> typesWHMList = session.createQuery("from TypesWHM where id_whmtype = " + documentType).getResultList();
        TypesWHM typesWHM = typesWHMList.get(0);

        String description = localization.getAkronim() + " " + typesWHM.getAkronim();
        Numerator numerator = new Numerator(0, description, true, typesWHM, 0, 0, localization);

        session.save(numerator);
        session.getTransaction().commit();
        session.close();
    }

    private static void validateNumerator(Numerator numerator, Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        if (calendar.get(Calendar.YEAR) > numerator.getLastUseYear()) {
            numerator.setLastUseYear(calendar.get(Calendar.YEAR));
            numerator.setLastUseMonth(calendar.get(Calendar.MONTH));
            resetCounter(numerator);
        } else if (calendar.get(Calendar.MONTH) > numerator.getLastUseMonth()) {
            numerator.setLastUseMonth(calendar.get(Calendar.MONTH));
            resetCounter(numerator);
        }
    }


    private static void resetCounter(Numerator numerator) {
        numerator.setValue(1);
    }

    private static void incrementCounter(Numerator numerator) {
        numerator.setValue(numerator.getValue() + 1);


    }

    /*
    public static getNumeratorId()
    SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Numerator.class)
            .buildSessionFactory();

    Session session = sessionFactory.getCurrentSession();
    Transaction tx = session.beginTransaction();

    List<User> userList = session.createQuery("from User where authToken = '" + authKey + "'").getResultList();
        session.getTransaction().
*/
}

