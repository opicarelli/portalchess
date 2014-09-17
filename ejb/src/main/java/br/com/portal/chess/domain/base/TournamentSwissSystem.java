package br.com.portal.chess.domain.base;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TOURNAMENT_SWISS_SYSTEM_T")
@ForeignKey(name = "tournament_swiss_system_tournament_fk")
public class TournamentSwissSystem extends Tournament {

    private static final long serialVersionUID = 3178706743941754932L;

    @Transient
    @Override
    public TournamentType getTournamentType() {
        return TournamentType.SWISS_SYSTEM;
    }

}