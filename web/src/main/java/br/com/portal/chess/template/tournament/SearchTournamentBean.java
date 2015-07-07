package br.com.portal.chess.template.tournament;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.portal.chess.domain.base.Tournament;
import br.com.portal.chess.domain.base.TournamentType;
import br.com.portal.chess.service.TournamentService;
import br.com.portal.chess.service.filter.TournamentFilter;
import br.com.portal.chess.utils.CipherUtils;

@ManagedBean(name = "searchTournament")
@ViewScoped
public class SearchTournamentBean implements Serializable {

    private static final long serialVersionUID = -22099190049213381L;

    @EJB
    private TournamentService tournamentService;

    private TournamentFilter filter;

    private List<Tournament> tournaments;

    public SearchTournamentBean() {
        filter = new TournamentFilter();
    }

    // -- Actions -- //

    public void onClickSearch() {
        tournaments = tournamentService.findByFilter(filter);
    }

    public String cipher(Object value) throws Exception {
        return CipherUtils.cipher(value.toString());
    }

    // -- Getters and Setters --/

    public TournamentFilter getFilter() {
        return filter;
    }

    public void setFilter(TournamentFilter filter) {
        this.filter = filter;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public TournamentType[] getTournamentTypes() {
        return TournamentType.values();
    }

    // -- Private methods -- //

}
