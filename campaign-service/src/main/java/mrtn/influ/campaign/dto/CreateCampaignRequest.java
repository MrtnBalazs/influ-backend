package mrtn.influ.campaign.dto;

public class CreateCampaignRequest {
    private String title;
    private String description;
    private Integer maxFee;
    private Integer minFee;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getMaxFee() {
        return maxFee;
    }

    public Integer getMinFee() {
        return minFee;
    }

    @Override
    public String toString() {
        return "CreateCampaignRequest{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", maxFee=" + maxFee +
                ", minFee=" + minFee +
                '}';
    }
}
