/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import domain.Leverancier;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import persistentie.JPAUtil;

/**
 *
 * @author Thomas
 */
public class LeverancierDAO {
    private static EntityManagerFactory emf;
    private EntityManager em;

    public LeverancierDAO() {
        emf = JPAUtil.getEntityManagerFactory();
    }

    public void addLeverancier(Leverancier leverancier) {
        em.persist(leverancier);
    }

    public List<Leverancier> findAll() {
        return em.createNamedQuery("Leverancier.findAll").getResultList();
    }

    public Leverancier findBy(int code) {
        return em.find(Leverancier.class, code);
    }

    public void update(Leverancier leverancier) {
        em.merge(leverancier);
    }

    public void delete(Leverancier leverancier) {
        em.remove(leverancier);
    }

    public void startTransaction() {
        if (em==null||!em.isOpen()) {
            em = emf.createEntityManager();
        }
        em.getTransaction().begin();
    }

    public void saveChanges() {
        em.getTransaction().commit();
        em.close();
    }

    public void stopTransaction() {
        em.close();
    }
}
