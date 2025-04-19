package mrtn.influ.dto;


public class GetCampaignResponse {
    private CampaignDto campaign;

    public GetCampaignResponse(CampaignDto campaign) {
        this.campaign = campaign;
    }

    public CampaignDto getCampaign() {
        return campaign;
    }

    public void setCampaign(CampaignDto campaign) {
        this.campaign = campaign;
    }
}
