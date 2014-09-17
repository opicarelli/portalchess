package br.com.portal.chess.service;

import java.io.Serializable;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.portal.chess.domain.base.User;

@Stateless
public class UserServiceBean implements UserService, Serializable {

    private static final long serialVersionUID = -4089442687237908421L;

    @Resource
    private SessionContext ctx;

    @EJB
    private UserServiceQuery userServiceQuery;

    @Override
    public User getActiveUser() {
        String username = ctx.getCallerPrincipal().getName();
        User user = userServiceQuery.findUserByUsername(username);
        if (user == null) {
            throw new RuntimeException("User not found: \'" + username + "\'.");
        }
        return user;
    }

}
