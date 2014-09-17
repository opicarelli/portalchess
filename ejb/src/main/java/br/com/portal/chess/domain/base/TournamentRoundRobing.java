package br.com.portal.chess.domain.base;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "TOURNAMENT_ROUND_ROBING_T")
@ForeignKey(name = "tournament_round_robing_tournament_fk")
public class TournamentRoundRobing extends Tournament {

    private static final long serialVersionUID = 1067973818251214752L;

    private Flag turnReturn = Flag.NO;

    @Transient
    @Override
    public TournamentType getTournamentType() {
        return TournamentType.ROUND_ROBING;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "turn_return", nullable = false)
    public Flag getTurnReturn() {
        return turnReturn;
    }

    public void setTurnReturn(Flag turnReturn) {
        this.turnReturn = turnReturn;
    }
}