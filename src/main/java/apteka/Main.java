package apteka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
    public static void main(String[] args){
        SpringApplication.run(Main.class,args);
    }

/*
    public static void main(String[] args){
        SessionFactory sessionFactory  =  new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try{
            System.out.println("Writing object to database ...");
            User user = new User("asd","aaab");

            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();


        }finally {
            sessionFactory.close();
        }
    }*/
}

