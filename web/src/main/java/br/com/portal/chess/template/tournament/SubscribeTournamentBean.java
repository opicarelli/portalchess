package br.com.portal.chess.template.tournament;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import br.com.portal.chess.domain.base.Category;
import br.com.portal.chess.domain.base.Player;
import br.com.portal.chess.domain.base.TournamentRoundRobing;
import br.com.portal.chess.service.TournamentService;
import br.com.portal.chess.utils.CipherUtils;

@ManagedBean(name = "subscribeTournament")
@ViewScoped
public class SubscribeTournamentBean implements Serializable {

    private static final long serialVersionUID = -3183249661887445342L;

    private String id;

    @EJB
    private TournamentService tournamentService;

    private TournamentRoundRobing tournament;

    private List<Category> categories;

    private Category category;

    private Player player;

    // -- Actions -- //

    public void loadData() throws Exception {
        if (id == null) {
            throw new RuntimeException("Param id is required");
        }
        Long param = Long.valueOf(CipherUtils.decipher(id));
        tournament = tournamentService.findById(TournamentRoundRobing.class, param, false);
        categories = tournamentService.findCategoriesByTournamentAndActiveUser(tournament);
        if (categories.isEmpty()) {
            // TODO: Message
        } else {
            category = categories.get(0);
        }

        player = tournamentService.getPlayerByActiveUser();

    }

    public void onClickSelectCategory(AjaxBehaviorEvent ev) throws Exception {
        //
    }

    public void onClickSubscribe() {
        tournamentService.subscribe(category, player);

        // TODO: Redirect to some page
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

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    // -- Private methods -- //

}
