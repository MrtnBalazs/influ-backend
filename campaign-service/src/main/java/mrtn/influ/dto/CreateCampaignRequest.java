package mrtn.influ.dto;

public class CreateCampaignRequest {
    private String userId;
    private String title;
    private String description;
    private Integer maxFee;
    private Integer minFee;

    public String getUserId() {
        return userId;
    }

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
                "userId='" + userId + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", maxFee=" + maxFee +
                ", minFee=" + minFee +
                '}';
    }
}
