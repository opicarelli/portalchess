package br.com.portal.chess.service;

import javax.ejb.Local;

import br.com.portal.chess.domain.base.User;

@Local
public interface UserService {

    public abstract User getActiveUser();

}
