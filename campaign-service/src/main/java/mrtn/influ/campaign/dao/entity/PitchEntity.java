package mrtn.influ.campaign.dao.entity;

import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "pitch")
public class PitchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String ownerId;
    @Column
    private String title;
    @Column
    private String text;
    @Enumerated(EnumType.STRING)
    private PitchState state;
    @ManyToOne
    @JoinColumn(name = "campaignId")
    private CampaignEntity campaign;

    public PitchEntity() {}

    public PitchEntity(CampaignEntity campaign, String ownerId, String title, String text) {
        this.campaign = campaign;
        this.ownerId = ownerId;
        this.title = title;
        this.text = text;
        this.state = PitchState.PENDING;
    }

    @PreRemove
    private void removeFromCampaign() {
        campaign.getPitches().remove(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CampaignEntity getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignEntity campaign) {
        this.campaign = campaign;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public PitchState getState() {
        return state;
    }

    public void setState(PitchState state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "PitchEntity{" +
                "id=" + id +
                ", ownerId='" + ownerId + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", state=" + state +
                ", campaign=" + campaign +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PitchEntity that = (PitchEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(ownerId, that.ownerId) && Objects.equals(title, that.title) && Objects.equals(text, that.text) && state == that.state && Objects.equals(campaign, that.campaign);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ownerId, title, text, state, campaign);
    }

}
