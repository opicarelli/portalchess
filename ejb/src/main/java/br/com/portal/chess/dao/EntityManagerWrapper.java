package br.com.portal.chess.dao;

import java.net.URL;

import javax.ejb.Local;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Local
public interface EntityManagerWrapper {

    public <T> T persist(T t);

    public <T> T merge(T t);

    public void remove(Object t);

    public <T> T find(Class<T> type, Object id);

    public <T> T find(Class<T> type, Object id, ResolveLazy<T> resolver);

    public <T> T resolveLazy(T t, String... propertyNames);

    public void flush();

    public <T> T refresh(T entity);

    public Query createQuery(String arg0);

    public <T> TypedQuery<T> createQuery(String arg0, Class<T> resultClass);

    public <T> Query createNativeQuery(String arg0, Class<T> arg1);

    public Query createNativeQuery(String arg0);

    public void importSQL(URL url, String name);
}
