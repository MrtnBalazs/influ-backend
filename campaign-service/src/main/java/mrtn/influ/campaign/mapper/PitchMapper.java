package mrtn.influ.campaign.mapper;

import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.entity.PitchEntity;
import mrtn.influ.campaign.dto.CreatePitchRequest;
import mrtn.influ.campaign.dto.Pitch;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PitchMapper {

    public Pitch mapPitchEntity(PitchEntity pitchEntity) {
        Pitch pitch = new Pitch(pitchEntity.getId().intValue(), pitchEntity.getCampaign().getId().intValue(), pitchEntity.getOwnerId(), pitchEntity.getTitle());
        pitch.setText(pitchEntity.getText());
        return pitch;
    }

    public PitchEntity mapCreatePitchRequest(CreatePitchRequest createPitchRequest, CampaignEntity campaignEntity, String userId) {
        return new PitchEntity(campaignEntity, userId, createPitchRequest.getTitle(), createPitchRequest.getText());
    }

    public List<Pitch> mapPitchEntities(List<PitchEntity> pitchEntityList) {
        return pitchEntityList.stream().map(this::mapPitchEntity).toList();
    }
}
