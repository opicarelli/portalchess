package br.com.portal.chess.domain.base;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name = "PLAYER_T")
public class Player implements Serializable {

    private static final long serialVersionUID = 354880711205268821L;

    private Long id;
    private User user;
    private Title title;
    private List<Club> clubs;

    // TODO: Add Identificador FIDE or CBX
    // TODO: Add lists of rating

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ForeignKey(name = "player_user_fk")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "title", nullable = false)
    @Enumerated(EnumType.STRING)
    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    @ManyToMany
    @JoinTable(name = "CLUB_PLAYER_T", joinColumns = { @JoinColumn(name = "player_id", referencedColumnName = "id") }, inverseJoinColumns = { @JoinColumn(name = "club_id", referencedColumnName = "id") })
    @ForeignKey(name = "club_player_player_fk", inverseName = "club_player_club_fk")
    public List<Club> getClubs() {
        return clubs;
    }

    public void setClubs(List<Club> clubs) {
        this.clubs = clubs;
    }

}
