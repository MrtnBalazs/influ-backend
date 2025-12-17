package mrtn.influ.campaign.dao.entity;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "campaign")
public class CampaignEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String ownerId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String contentGuideline;
    @Column
    private Integer fee;
    @Enumerated(EnumType.STRING)
    private CampaignType campaignType;
    @Enumerated(EnumType.STRING)
    private CampaignState state;
    @OneToMany(mappedBy = "campaign", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PitchEntity> pitches;

    // TODO favorited feature
//    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
//    @CollectionTable(name = "favoritedBys", joinColumns = @JoinColumn(name = "campaignId"))
//    @Column
//    private List<String> favoritedBy;

    public CampaignEntity() {}

    public CampaignEntity(String ownerId, String title, String description, String contentGuideline, Integer fee, CampaignType campaignType) {
        this.ownerId = ownerId;
        this.title = title;
        this.description = description;
        this.contentGuideline = contentGuideline;
        this.fee = fee;
        this.campaignType = campaignType;
        this.state = CampaignState.PENDING;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
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

    public String getContentGuideline() {
        return contentGuideline;
    }

    public void setContentGuideline(String contentGuideline) {
        this.contentGuideline = contentGuideline;
    }

    public Integer getFee() {
        return fee;
    }

    public void setFee(Integer fee) {
        this.fee = fee;
    }

    public CampaignType getCampaignType() {
        return campaignType;
    }

    public void setCampaignType(CampaignType campaignType) {
        this.campaignType = campaignType;
    }

    public List<PitchEntity> getPitches() {
        return pitches;
    }

    public void setPitches(List<PitchEntity> pitches) {
        this.pitches = pitches;
    }

    public CampaignState getState() {
        return state;
    }

    public void setState(CampaignState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "CampaignEntity{" +
                "id=" + id +
                ", ownerId='" + ownerId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", contentGuideline='" + contentGuideline + '\'' +
                ", fee=" + fee +
                ", campaignType='" + campaignType + '\'' +
                ", state=" + state +
                ", pitches=" + pitches +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CampaignEntity that = (CampaignEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(ownerId, that.ownerId) && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(contentGuideline, that.contentGuideline) && Objects.equals(fee, that.fee) && Objects.equals(campaignType, that.campaignType) && state == that.state && Objects.equals(pitches, that.pitches);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, title, description, contentGuideline, fee, campaignType, state, pitches);
    }

}