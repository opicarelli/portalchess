package br.com.portal.chess.domain.base;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "PAIRING_T")
public class Pairing implements Serializable {

    private static final long serialVersionUID = -4228906846034872902L;

    private Long id;
    private Long number;
    private PlayerCard white;
    private PlayerCard black;
    private Round round;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "number", nullable = false)
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    @OneToOne
    @JoinColumn(name = "player_card_white_id", foreignKey =  @ForeignKey(name = "pairing_player_card_white_fk"))
    public PlayerCard getWhite() {
        return white;
    }

    public void setWhite(PlayerCard white) {
        this.white = white;
    }

    @OneToOne
    @JoinColumn(name = "player_card_black_id", foreignKey = @ForeignKey(name = "pairing_player_card_black_fk"))
    public PlayerCard getBlack() {
        return black;
    }

    public void setBlack(PlayerCard black) {
        this.black = black;
    }

    @ManyToOne
    @JoinColumn(name = "round_id", nullable = false, foreignKey = @ForeignKey(name = "pairing_round_fk"))
    public Round getRound() {
        return round;
    }

    public void setRound(Round round) {
        this.round = round;
    }

}
