package br.com.portal.chess.service;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.portal.chess.dao.EntityManagerWrapper;
import br.com.portal.chess.domain.base.User;

@Stateless
public class UserServiceQueryBean implements UserServiceQuery, Serializable {

    private static final long serialVersionUID = 2090131855794891259L;

    @EJB
    private EntityManagerWrapper emw;

    @Override
    public User findUserById(Long id) {
        return emw.find(User.class, id);
    }

    @Override
    public User findUserByUsername(String username) {
        TypedQuery<User> query = emw.createQuery("FROM " + User.class.getSimpleName() + " u WHERE u.username = :username", User.class);
        query.setParameter("username", username);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findUserByUsernameAndPassword(String username, String password) {
        TypedQuery<User> query = emw.createQuery("FROM " + User.class.getSimpleName()
                + " u WHERE u.username = :username AND u.password = :password", User.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

}
