package mrtn.influ.campaign.mapper;

import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.entity.CampaignType;
import mrtn.influ.campaign.dto.Campaign;
import mrtn.influ.campaign.dto.CreateCampaignRequest;
import mrtn.influ.campaign.dto.Pitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CampaignMapper {

    @Autowired
    private PitchMapper pitchMapper;

    public Campaign mapCampaign(CampaignEntity campaignEntity) {
        List<Pitch> pitchDtos = campaignEntity.getPitches().stream().map(
                pitchEntity -> pitchMapper.mapPitchEntity(pitchEntity)
        ).toList();
        Campaign campaign = new Campaign(
                campaignEntity.getId().intValue(),
                campaignEntity.getOwnerId(),
                campaignEntity.getTitle(),
                Campaign.CampaignTypeEnum.valueOf(campaignEntity.getCampaignType().toString()),
                Campaign.CampaignStateEnum.valueOf(campaignEntity.getState().toString())
        );
        campaign.setDescription(campaignEntity.getDescription());
        campaign.setContentGuideline(campaignEntity.getContentGuideline());
        campaign.setFee(campaignEntity.getFee());
        campaign.setPitchList(pitchDtos);
        return campaign;
    }

    public CampaignEntity mapCreateCampaignRequest(CreateCampaignRequest createCampaignRequest, String ownerId) {
        return new CampaignEntity(
                ownerId,
                createCampaignRequest.getTitle(),
                createCampaignRequest.getDescription(),
                createCampaignRequest.getContentGuideline(),
                createCampaignRequest.getFee(),
                CampaignType.valueOf(createCampaignRequest.getCampaignType().name())
        );
    }
}
