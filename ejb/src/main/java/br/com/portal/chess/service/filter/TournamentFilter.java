package br.com.portal.chess.service.filter;

import java.io.Serializable;

import br.com.portal.chess.domain.base.TournamentType;

public class TournamentFilter implements Serializable {

    private static final long serialVersionUID = 5736335746883242448L;

    private TournamentType type;

    public TournamentType getType() {
        return type;
    }

    public void setType(TournamentType type) {
        this.type = type;
    }

}
