package br.com.portal.chess.template.layout;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.portal.chess.service.UserService;

@ManagedBean(name = "footer")
@SessionScoped
public class FooterBean implements Serializable {

    private static final long serialVersionUID = -109693538541361682L;

    private UserService userService;

    private String name;

    public FooterBean() {
        name = getUserService().getActiveUser().getUsername();
    }

    public String getName() {
        return name;
    }

    public void setName(String activeUser) {
        this.name = activeUser;
    }

    private UserService getUserService() {
        if (userService == null) {
            try {
                Context ctx = new InitialContext();
                userService = (UserService) ctx.lookup("java:app/portal-chess-ejb/UserServiceBean");
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
        return userService;
    }

}
