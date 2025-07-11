package mrtn.influ.campaign.mapper;

import mrtn.influ.campaign.dto.CampaignDto;
import mrtn.influ.campaign.dto.CreateCampaignRequest;
import mrtn.influ.campaign.dto.PitchDto;
import mrtn.influ.campaign.entity.CampaignEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CampaignMapper {

    @Autowired
    private PitchMapper pitchMapper;

    public CampaignDto mapCampaign(CampaignEntity campaignEntity, Boolean favorited) {
        List<PitchDto> pitchDtos = campaignEntity.getPitches().stream().map(
                pitchEntity -> pitchMapper.mapPitchEntity(pitchEntity)
        ).toList();
        return new CampaignDto(
                campaignEntity.getId(),
                campaignEntity.getUserId(),
                campaignEntity.getTitle(),
                campaignEntity.getDescription(),
                campaignEntity.getMaxFee(),
                campaignEntity.getMinFee(),
                favorited,
                pitchDtos);
    }

    public CampaignEntity mapCreateCampaignRequest(CreateCampaignRequest createCampaignRequest, String userId) {
        return new CampaignEntity(
                userId,
                createCampaignRequest.getTitle(),
                createCampaignRequest.getDescription(),
                createCampaignRequest.getMaxFee(),
                createCampaignRequest.getMinFee());
    }
}
