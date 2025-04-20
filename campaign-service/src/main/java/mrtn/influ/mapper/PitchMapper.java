package mrtn.influ.mapper;

import mrtn.influ.dto.CreatePitchRequest;
import mrtn.influ.dto.PitchDto;
import mrtn.influ.entity.CampaignEntity;
import mrtn.influ.entity.PitchEntity;
import org.springframework.stereotype.Component;

@Component
public class PitchMapper {

    public PitchDto mapPitchEntity(PitchEntity pitchEntity) {
        return new PitchDto(pitchEntity.getId(), pitchEntity.getCreatorId(), pitchEntity.getTitle(), pitchEntity.getText());
    }

    public PitchEntity mapCreatePitchRequest(CreatePitchRequest createPitchRequest, CampaignEntity campaignEntity, String userId) {
        return new PitchEntity(campaignEntity, userId, createPitchRequest.getTitle(), createPitchRequest.getText());
    }
}
