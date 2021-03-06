package br.com.portal.chess.domain.base;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TOURNAMENT_SWISS_SYSTEM_T")
// TODO JPA 2.1 @ForeignKey(name = "tournament_swiss_system_tournament_fk")
public class TournamentSwissSystem extends Tournament {

    private static final long serialVersionUID = 3178706743941754932L;

    @Transient
    @Override
    public TournamentType getTournamentType() {
        return TournamentType.SWISS_SYSTEM;
    }

}