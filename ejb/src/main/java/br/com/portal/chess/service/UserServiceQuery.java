package br.com.portal.chess.service;

import javax.ejb.Local;

import br.com.portal.chess.domain.base.User;

@Local
public interface UserServiceQuery {

    public abstract User findUserById(Long id);

    public abstract User findUserByUsername(String username);

    public abstract User findUserByUsernameAndPassword(String username, String password);
}
