package mrtn.influ.mapper;

import mrtn.influ.dto.CampaignDto;
import mrtn.influ.dto.CreateCampaignRequest;
import mrtn.influ.dto.PitchDto;
import mrtn.influ.entity.CampaignEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CampaignMapper {

    public CampaignDto mapCampaign(CampaignEntity campaignEntity, Boolean favorited) {
        List<PitchDto> pitchDtos = campaignEntity.getPitches().stream().map(
                pitch -> new PitchDto(pitch.getId(), pitch.getCreatorId(), pitch.getTitle(), pitch.getText())
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
