package br.com.portal.chess.domain.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TOURNAMENT_T")
public abstract class Tournament implements Serializable {

    private static final long serialVersionUID = -8062271501905066322L;

    private Long id;
    private String name;
    private String description;
    private Date dateFrom;
    private Date dateTo;
    private Date registrationLimitDate;
    private Long minPlayers = Long.valueOf(2);
    private Long maxPlayers;
    private List<Category> categories;
    private Flag active = Flag.NO;

    @Transient
    public abstract TournamentType getTournamentType();

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

    @Column(name = "description", nullable = true)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_from", nullable = false)
    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_to", nullable = false)
    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "registration_limit_date", nullable = false)
    public Date getRegistrationLimitDate() {
        return registrationLimitDate;
    }

    public void setRegistrationLimitDate(Date registrationLimitDate) {
        this.registrationLimitDate = registrationLimitDate;
    }

    @Column(name = "min_players", nullable = false)
    public Long getMinPlayers() {
        return minPlayers;
    }

    public void setMinPlayers(Long minPlayers) {
        this.minPlayers = minPlayers;
    }

    @Column(name = "max_players")
    public Long getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(Long maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    @OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL)
    public List<Category> getCategories() {
        if (categories == null) {
            categories = new ArrayList<>();
        }
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "active", nullable = false)
    public Flag getActive() {
        return active;
    }

    public void setActive(Flag active) {
        this.active = active;
    }

    // -- Utils -- //

    public void addCategory(Category category) {
        category.setTournament(this);
        getCategories().add(category);
    }

    public void addCategories(List<Category> categories) {
        for (Category category : categories) {
            addCategory(category);
        }
    }
}
