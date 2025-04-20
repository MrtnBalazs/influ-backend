package mrtn.influ.dto;

import java.util.List;

public class GetAllCampaignsResponse {
    private List<CampaignDto> campaigns;

    public GetAllCampaignsResponse(List<CampaignDto> campaigns) {
        this.campaigns = campaigns;
    }

    public List<CampaignDto> getCampaigns() {
        return campaigns;
    }

    public void setCampaigns(List<CampaignDto> campaigns) {
        this.campaigns = campaigns;
    }
}
