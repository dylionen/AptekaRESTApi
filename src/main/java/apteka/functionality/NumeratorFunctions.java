package apteka.functionality;

import apteka.tables.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

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
        Numerator numerator;


        List<Numerator> numeratorList = session.createQuery("from Numerator num where num.idLocalization.idLocalization = "
                + idLocalization + " and num.idWHMType.id_whmtype = " + documentType).getResultList();

        if (numeratorList.size() == 1) {
            numerator = numeratorList.get(0);
        } else {
            numerator = null;
            return "error";
        }

        validateNumerator(numerator, date);

        returnValue = String.format("%s/%d/%d/%d/%s", numerator.getIdWHMType().getAkronim(),
                numerator.getValue(), numerator.getLastUseMonth(), numerator.getLastUseYear(), numerator.getIdLocalization().getAkronim());

        incrementCounter(numerator);
        session.getTransaction().commit();
        session.close();

        return returnValue;
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

