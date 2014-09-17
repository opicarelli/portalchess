package br.com.portal.chess.template.tournament;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.portal.chess.domain.base.TournamentRoundRobing;
import br.com.portal.chess.service.TournamentService;
import br.com.portal.chess.utils.CipherUtils;

@ManagedBean(name = "viewTournament")
@ViewScoped
public class ViewTournamentBean implements Serializable {

    private static final long serialVersionUID = -5892496396700700791L;

    private String id;

    private TournamentService tournamentService;

    private TournamentRoundRobing tournament;

    // -- Actions -- //

    public void loadTournament() throws Exception {
        if (id == null) {
            throw new RuntimeException("Param id is required");
        }
        Long param = Long.valueOf(CipherUtils.decipher(id));
        tournament = getTournamentService().findById(TournamentRoundRobing.class, param, true);
    }

    // -- Getters and Setters --/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TournamentRoundRobing getTournament() {
        return tournament;
    }

    public void setTournament(TournamentRoundRobing tournament) {
        this.tournament = tournament;
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
