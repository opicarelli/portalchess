package br.com.portal.chess.domain.base;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TOURNAMENT_ELIMINATION_T")
@ForeignKey(name = "tournament_elimination_tournament_fk")
public class TournamentElimination extends Tournament {

    private static final long serialVersionUID = 6362199402665966505L;

    @Transient
    @Override
    public TournamentType getTournamentType() {
        return TournamentType.ELIMINATION;
    }

}