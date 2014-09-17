package br.com.portal.chess.template.tournament;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.portal.chess.domain.base.Category;
import br.com.portal.chess.domain.base.Flag;
import br.com.portal.chess.domain.base.Gender;
import br.com.portal.chess.domain.base.TournamentRoundRobing;
import br.com.portal.chess.service.TournamentService;
import br.com.portal.chess.utils.CipherUtils;

@ManagedBean(name = "createTournament")
@ViewScoped
public class CreateTournamentBean implements Serializable {

    private static final long serialVersionUID = -4165988985530404188L;

    private ResourceBundle bundle;

    private TournamentService tournamentService;

    private TournamentRoundRobing tournament;
    private Category category;

    public CreateTournamentBean() {
        tournament = new TournamentRoundRobing();
        category = new Category();
    }

    @PostConstruct
    private void init() {
        FacesContext context = FacesContext.getCurrentInstance();
        bundle = context.getApplication().getResourceBundle(context, "msgs");
    }

    // -- Actions -- //

    public void onClickSave(AjaxBehaviorEvent ev) throws Exception {
        if (formValid()) {
            tournament = getTournamentService().createRoundRobing(tournament);
            String param = CipherUtils.cipher(String.valueOf(tournament.getId()));
            FacesContext.getCurrentInstance().getExternalContext().redirect("view.jsf?id=" + param);
        }
    }

    public void onClickAddCategory(AjaxBehaviorEvent ev) {
        tournament.addCategory(category);
        category = new Category();
    }

    public void onClickRemoveCategory() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String index = params.get("idx");
        tournament.getCategories().remove(Integer.parseInt(index));
    }

    public void onChangeAbsoluteGender(AjaxBehaviorEvent ev) {
        //
    }

    public void onChangeAbsoluteAge(AjaxBehaviorEvent ev) {
        //
    }

    private boolean formValid() {
        boolean valid = true;
        if (tournament.getCategories().isEmpty()) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    bundle.getString("page.tournament.create.error.category.required"), null);
            context.addMessage("messagesCategory", message);
            valid = false;
        }
        return valid;
    }

    // -- Getters and Setters --/

    public TournamentRoundRobing getTournament() {
        return tournament;
    }

    public void setTournament(TournamentRoundRobing tournament) {
        this.tournament = tournament;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDataDateNow() {
        Calendar c = Calendar.getInstance();
        int unroundedMinutes = c.get(Calendar.MINUTE);
        int mod = unroundedMinutes % 5;
        c.add(Calendar.MINUTE, mod == 0 ? 5 : 5 - mod);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return new SimpleDateFormat(bundle.getString("pattern.date.time")).format(c.getTime());
    }

    public Flag[] getYesOrNo() {
        return Flag.values();
    }

    public Gender[] getGenders() {
        return Gender.values();
    }

    // -- Private methods -- //

    private TournamentService getTournamentService() {
        if (tournamentService == null) {
            try {
                Context ctx = new InitialContext();
                tournamentService = (TournamentService) ctx.lookup("java:app/portal-chess-ejb/TournamentServiceBean");
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
        return tournamentService;
    }

}
