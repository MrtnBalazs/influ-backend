package mrtn.influ.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "campaign")
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String userId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Integer minFee;
    @Column
    private Integer maxFee;
    @OneToMany(mappedBy = "campaign", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PitchEntity> pitches;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "favoritedBys", joinColumns = @JoinColumn(name = "campaignId"))
    @Column
    private List<String> favoritedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMinFee() {
        return minFee;
    }

    public void setMinFee(Integer minFee) {
        this.minFee = minFee;
    }

    public Integer getMaxFee() {
        return maxFee;
    }

    public void setMaxFee(Integer maxFee) {
        this.maxFee = maxFee;
    }

    public List<PitchEntity> getPitches() {
        if(Objects.isNull(pitches))
            pitches = new ArrayList<>();
        return pitches;
    }

    public void setPitches(List<PitchEntity> pitches) {
        this.pitches = pitches;
    }

    public List<String> getFavoritedBy() {
        if(Objects.isNull(favoritedBy))
            favoritedBy = new ArrayList<>();
        return favoritedBy;
    }

    public void setFavoritedBy(List<String> favoritedBy) {
        this.favoritedBy = favoritedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CampaignEntity campaign = (CampaignEntity) o;
        return Objects.equals(id, campaign.id) && Objects.equals(userId, campaign.userId) && Objects.equals(title, campaign.title) && Objects.equals(description, campaign.description) && Objects.equals(minFee, campaign.minFee) && Objects.equals(maxFee, campaign.maxFee) && Objects.equals(pitches, campaign.pitches) && Objects.equals(favoritedBy, campaign.favoritedBy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, title, description, minFee, maxFee, pitches, favoritedBy);
    }

    @Override
    public String toString() {
        return "CampaignEntity{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", minFee=" + minFee +
                ", maxFee=" + maxFee +
                ", pitches=" + pitches +
                ", favoritedBy=" + favoritedBy +
                '}';
    }
}