package apteka.controllers;

import apteka.functionality.AuthValidator;
import apteka.tables.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MovementsController {
    SessionFactory sessionFactory  =  new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(WHM.class)
            .addAnnotatedClass(WHMList.class)
            .addAnnotatedClass(UserType.class)
            .addAnnotatedClass(VATTable.class)
            .addAnnotatedClass(User.class)
            .addAnnotatedClass(TypesWHM.class)
            .addAnnotatedClass(Article.class)
            .buildSessionFactory();
    ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/createWM")
    public int createWM(@RequestHeader(value="Authorization") String authId){
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        User user = AuthValidator.getAuth(authId);
        if(user==null) return -1;

        List<TypesWHM> typesWHMS = session.createQuery("from TypesWHM where id_whmtype = 1").getResultList();

        WHM whm = new WHM(user,typesWHMS.get(0));
        session.save(whm);
        session.getTransaction().commit();
        return whm.getIdWh();
    }


    @GetMapping("getWMArticlesList/{WHMid}")
    public String getWMArticlesList(@PathVariable int WHMid, @RequestHeader(value="Authorization") String authId) throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        List<WHMList> whmLists;

        try{
            whmLists = session.createQuery("from WHMList where idWHM =" + WHMid).getResultList();
            tx.commit();
        }catch (Exception e){
            tx.rollback();
            return e.getMessage();
        } finally {
            //session.getTransaction().commit();
        }
        return objectMapper.writeValueAsString(whmLists);
    }


    @PostMapping("/createWMArticlesList/{WHMid}")
    public ResponseEntity createWMArticlesList(@PathVariable int WHMid, @RequestHeader(value="Authorization") String authId, @RequestBody List<WHMList> whmList) throws JsonProcessingException {
        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();

        List<WHM> whm = session.createQuery("from WHM where idWh =" + WHMid).getResultList();

        List<WHMList> whmNewList = new LinkedList<>();
        WHMList WHMListOne = null;

        List<Article> articles;
        List<VATTable> vat;


        for (WHMList whTmp:whmList){
            //Article idArticle, WHM idWHM, double value, VATTable idVATTable, double price

            articles = session.createQuery("from Article where idArticle=" + whTmp.getForeignIdArticle()).getResultList();
            vat = session.createQuery("from VATTable where idVat = " + whTmp.getForeignIdVATTable()).getResultList();
            WHMListOne = new WHMList(articles.get(0),whm.get(0),whTmp.getValue(),vat.get(0),whTmp.getPrice());
            whmNewList.add(WHMListOne);
            session.save(WHMListOne);
        }

        tx.commit();

        return ResponseEntity.ok(HttpStatus.OK);
    }

}
