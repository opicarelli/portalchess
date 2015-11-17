package br.com.portal.chess;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JPAUnitTest {

    private EntityManager em;

    @Before
    public void before() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("portal-chess");
        em = factory.createEntityManager();
        em.getTransaction().begin();
    }

    @After
    public void after() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
        em.close();
    }

    @Test
    public void createEMTest() {
        Assert.assertNotNull(em);
    }
}
