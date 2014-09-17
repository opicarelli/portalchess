package br.com.portal.chess.domain.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "CATEGORY_T")
public class Category implements Serializable {

    private static final long serialVersionUID = -8344818645486135595L;

    private Long id;
    private String name;
    private String description;
    private Flag absoluteGender = Flag.YES;
    private Gender gender;
    private Flag absoluteAge = Flag.YES;
    private Integer startAge;
    private Integer endAge;
    private Tournament tournament;
    private List<Round> rounds;
    private List<PlayerCard> playerCards;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "absolute_gender", nullable = false)
    public Flag getAbsoluteGender() {
        return absoluteGender;
    }

    public void setAbsoluteGender(Flag absoluteGender) {
        this.absoluteGender = absoluteGender;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "absolute_age", nullable = false)
    public Flag getAbsoluteAge() {
        return absoluteAge;
    }

    public void setAbsoluteAge(Flag absoluteAge) {
        this.absoluteAge = absoluteAge;
    }

    @Column(name = "start_age")
    public Integer getStartAge() {
        return startAge;
    }

    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }

    @Column(name = "end_age")
    public Integer getEndAge() {
        return endAge;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    @ForeignKey(name = "category_tournamet_fk")
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    public List<PlayerCard> getPlayerCards() {
        if (playerCards == null) {
            playerCards = new ArrayList<>();
        }
        return playerCards;
    }

    public void setPlayerCards(List<PlayerCard> playerCards) {
        this.playerCards = playerCards;
    }

    // -- Utils -- //

    public void addPlayerCard(Player player) {
        PlayerCard card = new PlayerCard();
        card.setPlayer(player);
        card.setCategory(this);
        card.setPoints(Double.valueOf(0.0));
        getPlayerCards().add(card);
    }

}
