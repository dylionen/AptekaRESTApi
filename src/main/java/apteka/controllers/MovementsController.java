package apteka.controllers;

import apteka.functionality.AuthValidator;
import apteka.functionality.FixStocks;
import apteka.tables.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MovementsController {
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
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/createWM")
    public int createWM(@RequestHeader(value = "Authorization") String authId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        User user = AuthValidator.getAuth(authId);
        if (user == null) return -1;

        List<TypesWHM> typesWHMS = session.createQuery("from TypesWHM where id_whmtype = 1").getResultList();
        List<Localization> listLocalizations = session.createQuery("from Localization").getResultList();

        WHM whm = new WHM(user, typesWHMS.get(0), 0.0, false, 0.0, "T", listLocalizations.get(0), new Date());
        session.save(whm);
        session.getTransaction().commit();
        return whm.getIdWh();
    }


    @PostMapping("/createPM")
    public int createPM(@RequestHeader(value = "Authorization") String authId,
                        @RequestHeader(value = "Localization") int localization,
                        @RequestBody WHM jsonWHM) {

        System.out.println(jsonWHM.getForeignName());
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        User user = AuthValidator.getAuth(authId);
        if (user == null) return -1;

        List<TypesWHM> typesWHMS = session.createQuery("from TypesWHM where id_whmtype = 2").getResultList();
        List<Localization> listLocalizations = session.createQuery("from Localization where idLocalization = " + localization).getResultList();

        WHM whm = new WHM(user, typesWHMS.get(0), jsonWHM.getPrice(), jsonWHM.isBufor(), jsonWHM.getPriceB(),
                jsonWHM.getForeignName(), listLocalizations.get(0), new Date());
        session.save(whm);
        session.getTransaction().commit();

        if (!jsonWHM.isBufor()) {
            FixStocks.calculateQuantityOfCurrentWHM(whm.getIdWh());
        }

        return whm.getIdWh();
    }

    @GetMapping("/getPM")
    public String getPM(@RequestHeader(value = "Authorization") String authId, @RequestHeader(value = "Localization") int localization) throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<WHM> WH = null;

        try {
            WH = session.createQuery("from WHM where idTypeWHM = 2 and idLocalization = " + localization + " order by createdDate DESC ").getResultList();
            System.out.println(WH);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            return e.getMessage();
        } finally {
            //session.getTransaction().commit();
        }
        return objectMapper.writeValueAsString(WH);
    }


    @GetMapping("/getPM/{PMid}")
    public String getPMCurrent(@PathVariable int PMid, @RequestHeader(value = "Authorization") String authId) throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<WHM> WH = null;

        try {
            WH = session.createQuery("from WHM where idWh =" + PMid).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            return e.getMessage();
        } finally {
            //session.getTransaction().commit();
        }
        return objectMapper.writeValueAsString(WH.get(0));
    }


    @GetMapping("getWMArticlesList/{WHMid}")
    public String getWMArticlesList(@PathVariable int WHMid, @RequestHeader(value = "Authorization") String authId) throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<WHMList> whmLists;

        try {
            whmLists = session.createQuery("from WHMList where id_warehouse_movement =" + WHMid).getResultList();
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            return e.getMessage();
        } finally {
            //session.getTransaction().commit();
        }
        return objectMapper.writeValueAsString(whmLists);
    }


    @PostMapping("/createWMArticlesList/{WHMid}")
    public ResponseEntity createWMArticlesList(@PathVariable int WHMid, @RequestHeader(value = "Authorization") String authId,
                                               @RequestBody List<WHMList> whmList, @RequestHeader(value = "Localization") int localization)
            throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<WHM> whm = session.createQuery("from WHM where idWh =" + WHMid).getResultList();

        List<WHMList> whmNewList = new LinkedList<>();
        WHMList WHMListOne = null;

        List<Article> articles;
        List<VATTable> vat;
        System.out.println(Arrays.toString(whmList.toArray()));


        for (WHMList whTmp : whmList) {

            //Article idArticle, WHM idWHM, double value, VATTable idVATTable, double price


            articles = session.createQuery("from Article where idArticle=" + whTmp.getForeignIdArticle()).getResultList();
            vat = session.createQuery("from VATTable where idVat = " + whTmp.getForeignIdVATTable()).getResultList();


            WHMListOne = new WHMList(articles.get(0), whm.get(0), whTmp.getValue(), vat.get(0), whTmp.getPrice());
            whmNewList.add(WHMListOne);
            session.save(WHMListOne);

            //Chwilowo brak zasotosowania
            //FixStocks.calculateQuantity(whTmp.getForeignIdArticle(),localization);
        }

        tx.commit();

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/getVAT")
    public String createWMArticlesList(@RequestHeader(value = "Authorization") String authId) throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        List<VATTable> vatTables = null;

        Transaction tx = session.beginTransaction();

        try {
            vatTables = session.createQuery("from VATTable").getResultList();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            session.getTransaction().commit();
            session.close();
            //session.getTransaction().commit();
        }
        return objectMapper.writeValueAsString(vatTables);
    }


    @PutMapping("/confirmPW/{WHMid}")
    public ResponseEntity confirmPw(@PathVariable int WHMid) throws JsonProcessingException {

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<WHM> whmList = session.createQuery("from WHM where idWh = " + WHMid).getResultList();
        if (whmList.size() != 1) {
            return new ResponseEntity<>(
                    "Brak dokumentu o przesłanych parametrach.",
                    HttpStatus.BAD_REQUEST);
        }
        WHM whm = whmList.get(0);
        whm.setBufor(false);


        session.getTransaction().commit();
        session.close();
        FixStocks.calculateQuantityOfCurrentWHM(whm.getIdWh());

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/countArticle/{ArticleId}")
    public String countArticle(@RequestHeader(value = "Localization") int localization,
                               @RequestHeader(value = "Authorization") String authId, @PathVariable String ArticleId) {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        User user = AuthValidator.getAuth(authId);
        if (user == null) return "Nieprawidlowe";


        System.out.println(localization);

        Query sumQuery = session.createQuery(
                "select SUM(value) from WHMList wl where idArticle = " + ArticleId + " and  wl.idWHM.idLocalization = " + localization);
        Double ilosc = sumQuery.list().get(0) != null ? Double.valueOf(sumQuery.list().get(0).toString()) : 0.0;
        session.getTransaction().commit();
        session.close();


        return ilosc.toString();
    }

}
