package mrtn.influ.campaign.service;

import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.entity.PitchEntity;
import mrtn.influ.campaign.dao.entity.PitchState;
import mrtn.influ.campaign.dao.repository.CampaignRepository;
import mrtn.influ.campaign.dao.repository.PitchRepository;
import mrtn.influ.campaign.dto.CreatePitchRequest;
import mrtn.influ.campaign.dto.Pitch;
import mrtn.influ.campaign.exception.ErrorCode;
import mrtn.influ.campaign.mapper.PitchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static mrtn.influ.campaign.exception.ErrorCode.NOT_AUTHORISED_TO_GET;
import static mrtn.influ.campaign.exception.ErrorCode.PITCH_NOT_FOUND;

@Service
public class PitchService {

    @Autowired
    private PitchRepository pitchRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private PitchMapper pitchMapper;
    @Autowired
    private CampaignService campaignService;

    private final Map<PitchState, Set<PitchState>> ALLOWED_PITCH_STATE_TRANSITIONS =
            Map.of(
                    PitchState.PENDING, Set.of(
                            PitchState.SELECTED,
                            PitchState.REJECTED
                    ),
                    PitchState.SELECTED, Set.of(
                            PitchState.PENDING,
                            PitchState.ACCEPTED,
                            PitchState.REJECTED
                    ),
                    PitchState.ACCEPTED, Set.of(
                            PitchState.DONE
                    ),
                    PitchState.DONE, Set.of(),
                    PitchState.REJECTED, Set.of()
            );

    public void createPitch(CreatePitchRequest createPitchRequest, String userId) {
        // TODO megnézni, hogy már nem csinált-e pitch-t
        CampaignEntity campaignEntity = campaignRepository
                .findById(createPitchRequest.getCampaignId().longValue())
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.toException(createPitchRequest.getCampaignId().toString()));

        PitchEntity pitchEntity = pitchMapper.mapCreatePitchRequest(createPitchRequest, campaignEntity, userId);
        pitchRepository.save(pitchEntity);
    }

    public void deletePitch(Integer id, String xUserId) {
        PitchEntity pitchEntity = pitchRepository.findById(id.longValue()).orElseThrow(() -> PITCH_NOT_FOUND.toException(id.toString()));
        if(!pitchEntity.getOwnerId().equals(xUserId))
            ErrorCode.NOT_AUTHORISED_TO_DELETE.throwException(id.toString());
        pitchRepository.delete(pitchEntity);
    }

    public void updatePitchState(Integer id, String xUserId, String pitchState) {
        PitchEntity pitchEntity = pitchRepository.findById(id.longValue()).orElseThrow(() -> PITCH_NOT_FOUND.toException(id.toString()));
        if(!pitchEntity.getCampaign().getOwnerId().equals(xUserId))
            ErrorCode.NOT_AUTHORISED_TO_DELETE.throwException(id.toString());

        if(
                isPitchStateUpdateValid(pitchEntity.getState(), PitchState.valueOf(pitchState)) &&
                otherPitchesAreAllPending(pitchEntity)
        ) {
             pitchEntity.setState(PitchState.valueOf(pitchState));
             campaignService.setCampaignStatusBaseOnPitchStateChange(PitchState.valueOf(pitchState), pitchEntity.getCampaign());
        } else {
            ErrorCode.INVALID_STATE_CHANGE.throwException(pitchEntity.getId().toString(), pitchEntity.getState().name(), PitchState.valueOf(pitchState).name());
        }
    }

    public List<Pitch> getPitchesForUser(String userId) {
        List<PitchEntity> pitchEntities = pitchRepository.findByOwnerId(userId);
        return pitchMapper.mapPitchEntities(pitchEntities);
    }

    public Pitch getPitch(String userId, Integer pitchId) {
        PitchEntity pitchEntity = pitchRepository.findById(pitchId).orElseThrow(() -> PITCH_NOT_FOUND.toException(pitchId.toString()));
        if(!pitchEntity.getOwnerId().equals(userId) && !pitchEntity.getCampaign().getOwnerId().equals(userId)) {
            NOT_AUTHORISED_TO_GET.throwException(userId);
        }
        return pitchMapper.mapPitchEntity(pitchEntity);
    }

    private boolean isPitchStateUpdateValid(PitchState oldState, PitchState newState) {
        return ALLOWED_PITCH_STATE_TRANSITIONS.get(oldState).contains(newState);
    }

    private boolean otherPitchesAreAllPending(PitchEntity pitch) {
        return pitch.getCampaign().getPitches().stream().filter(p -> !p.equals(pitch)).allMatch(otherPitch -> otherPitch.getState().equals(PitchState.PENDING));
    }

}
