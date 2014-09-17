package br.com.portal.chess.service;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.joda.time.Years;

import br.com.portal.chess.dao.EntityManagerWrapper;
import br.com.portal.chess.domain.base.Category;
import br.com.portal.chess.domain.base.Flag;
import br.com.portal.chess.domain.base.Player;
import br.com.portal.chess.domain.base.Title;
import br.com.portal.chess.domain.base.Tournament;
import br.com.portal.chess.domain.base.TournamentElimination;
import br.com.portal.chess.domain.base.TournamentRoundRobing;
import br.com.portal.chess.domain.base.TournamentSwissSystem;
import br.com.portal.chess.domain.base.User;
import br.com.portal.chess.service.filter.TournamentFilter;

@Stateless
public class TournamentServiceBean implements TournamentService, Serializable {

    private static final long serialVersionUID = 7304131797943419798L;

    @EJB
    private EntityManagerWrapper emw;

    @EJB
    private UserService userService;

    @Override
    public <T extends Tournament> T findById(Class<T> clazz, Long id, boolean resolveLazy) {
        T tournament = emw.find(clazz, id);
        if (resolveLazy) {
            for (Category c : tournament.getCategories()) {
                c.getPlayerCards().size();
            }
        }
        return tournament;
    }

    @Override
    public List<Tournament> findByFilter(TournamentFilter filter) {
        StringBuilder jpql = new StringBuilder("SELECT t FROM ");
        if (filter.getType() != null) {
            switch (filter.getType()) {
            case ROUND_ROBING:
                jpql.append(TournamentRoundRobing.class.getSimpleName());
                break;
            case SWISS_SYSTEM:
                jpql.append(TournamentSwissSystem.class.getSimpleName());
                break;
            case ELIMINATION:
                jpql.append(TournamentElimination.class.getSimpleName());
                break;
            default:
                throw new RuntimeException("Invalid type: " + filter.getType().name());
            }
        } else {
            jpql.append(Tournament.class.getSimpleName());
        }
        jpql.append(" t ");
        jpql.append("WHERE 1 > 0");

        TypedQuery<Tournament> query = emw.createQuery(jpql.toString(), Tournament.class);
        return query.getResultList();
    }

    @Override
    public TournamentRoundRobing createRoundRobing(TournamentRoundRobing tournament) {
        TournamentRoundRobing persist = new TournamentRoundRobing();
        setTournamentBase(persist, tournament);
        persist.setTurnReturn(tournament.getTurnReturn());
        return emw.persist(persist);
    }

    @Override
    public void subscribe(Category category, Player player) {

        // TODO: extract to server save player
        if (player.getId() == null) {
            player = emw.persist(player);
        } else {
            player = emw.merge(player);
        }

        // TODO: Check if the current date is less than limit date

        category = emw.find(Category.class, category.getId());
        emw.resolveLazy(category, "playerCards");
        category.addPlayerCard(player);

        emw.merge(category);
    }

    @Override
    public Player findPlayerByUser(User user) {
        try {
            TypedQuery<Player> query = emw.createQuery("FROM " + Player.class.getSimpleName() + " WHERE user.id = :userId", Player.class);
            query.setParameter("userId", user.getId());
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Player getPlayerByActiveUser() {
        User activeUser = userService.getActiveUser();
        Player player = findPlayerByUser(activeUser);
        if (player == null) {
            player = new Player();
            player.setUser(activeUser);
            player.setTitle(Title.NONE);
        }
        return player;
    }

    @Override
    public List<Category> findCategoriesByTournamentAndActiveUser(TournamentRoundRobing tournament) {
        User activeUser = userService.getActiveUser();
        DateTime birthdate = new DateTime(activeUser.getBirthdate());
        DateTime endTournament = new DateTime(tournament.getDateTo());
        Years yearsBetween = Years.yearsBetween(birthdate, endTournament);

        StringBuilder jpql = new StringBuilder("FROM " + Category.class.getSimpleName() + " c ");
        jpql.append("WHERE c.tournament.id = :tournamentId");
        jpql.append(" AND (c.absoluteGender = :absoluteGenderYes OR c.gender = :gender)");
        jpql.append(" AND (c.absoluteAge = :absoluteAgeYes OR (c.startAge <= :userAge AND c.endAge >= :userAge))");

        TypedQuery<Category> query = emw.createQuery(jpql.toString(), Category.class);
        query.setParameter("tournamentId", tournament.getId());
        query.setParameter("absoluteGenderYes", Flag.YES);
        query.setParameter("gender", activeUser.getGender());
        query.setParameter("absoluteAgeYes", Flag.YES);
        query.setParameter("userAge", yearsBetween.getYears());

        return query.getResultList();
    }

    private <T extends Tournament> void setTournamentBase(T persist, T tournament) {
        persist.setActive(Flag.NO);
        persist.setName(tournament.getName());
        persist.setDescription(tournament.getDescription());
        persist.setDateFrom(tournament.getDateFrom());
        persist.setDateTo(tournament.getDateTo());
        persist.setRegistrationLimitDate(tournament.getRegistrationLimitDate());
        persist.setMinPlayers(tournament.getMinPlayers());
        persist.setMaxPlayers(tournament.getMaxPlayers());
        persist.addCategories(tournament.getCategories());
    }
}
