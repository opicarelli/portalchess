package br.com.portal.chess.service;

import java.util.List;

import javax.ejb.Local;

import br.com.portal.chess.domain.base.Category;
import br.com.portal.chess.domain.base.Player;
import br.com.portal.chess.domain.base.Tournament;
import br.com.portal.chess.domain.base.TournamentRoundRobing;
import br.com.portal.chess.domain.base.User;
import br.com.portal.chess.service.filter.TournamentFilter;

@Local
public interface TournamentService {

    public <T extends Tournament> T findById(Class<T> clazz, Long id, boolean resolveLazy);

    public List<Tournament> findByFilter(TournamentFilter filter);

    public TournamentRoundRobing createRoundRobing(TournamentRoundRobing tournament);

    public void subscribe(Category category, Player player);

    public List<Category> findCategoriesByTournamentAndActiveUser(TournamentRoundRobing tournament);

    public Player findPlayerByUser(User user);

    public Player getPlayerByActiveUser();

}
