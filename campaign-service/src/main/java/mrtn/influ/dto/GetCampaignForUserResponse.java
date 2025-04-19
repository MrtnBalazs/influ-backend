package mrtn.influ.dto;

import java.util.ArrayList;
import java.util.List;

public class GetCampaignForUserResponse {
    private List<CampaignDto> campaigns;

    public GetCampaignForUserResponse(List<CampaignDto> campaigns) {
        this.campaigns = campaigns;
    }

    public List<CampaignDto> getCampaigns() {
        if(this.campaigns == null)
            return new ArrayList<>();
        return campaigns;
    }

    public void setCampaigns(List<CampaignDto> campaigns) {
        this.campaigns = campaigns;
    }

    @Override
    public String toString() {
        return "GetCampaignForUserResponse{" +
                "campaigns=" + campaigns +
                '}';
    }
}
