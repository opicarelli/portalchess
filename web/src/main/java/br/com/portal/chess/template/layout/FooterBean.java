package br.com.portal.chess.template.layout;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.com.portal.chess.service.UserService;

@ManagedBean(name = "footer")
@SessionScoped
public class FooterBean implements Serializable {

    private static final long serialVersionUID = -109693538541361682L;

    @EJB
    private UserService userService;

    private String name;

    @PostConstruct
    private void init() {
        name = userService.getActiveUser().getUsername();
    }

    public String getName() {
        return name;
    }

    public void setName(String activeUser) {
        this.name = activeUser;
    }

}
