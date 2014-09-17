package br.com.portal.chess.dao;

public interface ResolveLazy<T> {

    public void resolve(T bean);
}
