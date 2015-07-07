package br.com.portal.chess.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Stateless
public class EntityManagerWrapperBean implements EntityManagerWrapper, Serializable {

    private static final long serialVersionUID = -7735257387557544407L;

    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerWrapperBean.class);

    @PersistenceContext(unitName = "portal-chess")
    private EntityManager em;

    public <T> T find(Class<T> type, Object id) {
        return em.find(type, id);
    }

    public <T> T find(Class<T> type, Object id, ResolveLazy<T> resolver) {
        T element = find(type, id);
        resolver.resolve(element);
        return element;
    }

    public <T> T merge(T t) {
        return em.merge(t);
    }

    public <T> T persist(T t) {
        em.persist(t);
        return t;
    }

    public void remove(Object t) {
        t = em.merge(t);
        em.remove(t);
    }

    public <T> T resolveLazy(T t, String... propertyNames) {
        t = em.merge(t);
        for (String propertyName : propertyNames) {
            Collection<?> coll = findCollection(t, propertyName);
            if (coll != null) {
                coll.size();
            }
        }

        return t;
    }

    private <T> Collection<?> findCollection(T t, String propertyNames) {

        Method method;
        Object target = t;

        StringTokenizer tokenizer = new StringTokenizer(propertyNames, ".");
        while (tokenizer.hasMoreTokens()) {
            String prop = tokenizer.nextToken();
            StringBuilder builder = new StringBuilder(prop);
            builder.setCharAt(0, Character.toUpperCase(builder.charAt(0)));
            builder.insert(0, "get");
            try {
                method = target.getClass().getMethod(builder.toString());
                target = method.invoke(target);
            } catch (SecurityException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return (Collection<?>) target;
    }

    public Query createQuery(String arg0) {
        return em.createQuery(arg0);
    }

    public <T> TypedQuery<T> createQuery(String arg0, Class<T> resultClass) {
        return em.createQuery(arg0, resultClass);
    }

    public Query createNativeQuery(String arg0) {
        return em.createNativeQuery(arg0);
    }

    public <T> Query createNativeQuery(String arg0, Class<T> arg1) {
        return em.createNativeQuery(arg0, arg1);
    }

    public void flush() {
        em.flush();
    }

    public <T> T refresh(T entity) {
        em.refresh(entity);
        return entity;
    }

    private void importSQL(BufferedReader in, String name) {
        Connection conn = null;
        Statement s = null;
        try {
            InitialContext ic = new InitialContext();
            DataSource ds = (DataSource) ic.lookup(name);
            conn = ds.getConnection();
            s = conn.createStatement();

            String line = null;
            do {
                line = in.readLine();
                if (line != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("executeUpdate: " + line);
                    }
                    s.executeUpdate(line);
                }
            } while (line != null);

        } catch (NamingException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(s);
            close(conn);
        }
    }

    public void importSQL(URL url, String name) {
        LOG.info("Import sql file: " + url + " on " + name);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            importSQL(in, name);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.error("Error closing...", e);
                }
            }
        }
    }

    private void close(Statement s) {
        if (s != null) {
            try {
                s.close();
            } catch (SQLException e) {
                LOG.warn("Error closing", e);
            }
        }
    }

    private void close(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOG.warn("Error closing", e);
            }
        }
    }

}
