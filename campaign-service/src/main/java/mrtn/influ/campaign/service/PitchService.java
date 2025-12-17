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

    private final Map<PitchState, Set<PitchState>> PITCH_OWNER_ALLOWED_PITCH_STATE_TRANSITIONS =
            Map.of(
                    PitchState.PENDING, Set.of(),
                    PitchState.SELECTED, Set.of(),
                    PitchState.ACCEPTED, Set.of(PitchState.ABORTED),
                    PitchState.DONE, Set.of(),
                    PitchState.REJECTED, Set.of(),
                    PitchState.ABORTED, Set.of()
            );

    private final Map<PitchState, Set<PitchState>> CAMPAIGN_OWNER_ALLOWED_PITCH_STATE_TRANSITIONS =
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
                            PitchState.DONE,
                            PitchState.ABORTED
                    ),
                    PitchState.DONE, Set.of(),
                    PitchState.REJECTED, Set.of(),
                    PitchState.ABORTED, Set.of()
            );

    private final Set<PitchState> ALLOW_PITCH_DELETE_STATES = Set.of(PitchState.PENDING, PitchState.SELECTED, PitchState.ABORTED);
    private final Set<PitchState> ALLOW_OWN_PITCH_CREATE_STATES = Set.of(PitchState.REJECTED);

    public void createPitch(CreatePitchRequest createPitchRequest, String userId) {
        CampaignEntity campaignEntity = campaignRepository
                .findById(createPitchRequest.getCampaignId().longValue())
                .orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.toException(createPitchRequest.getCampaignId().toString()));

        if(campaignEntity.getPitches()
                .stream()
                .anyMatch(pitchEntity ->
                    (pitchEntity.getOwnerId().equals(userId) && !ALLOW_OWN_PITCH_CREATE_STATES.contains(pitchEntity.getState())) ||
                    (pitchEntity.getState() != PitchState.PENDING && pitchEntity.getState() != PitchState.SELECTED)
                ))
            ErrorCode.PITCH_CAN_NOT_BE_CREATED.throwException(createPitchRequest.getCampaignId().toString());

        PitchEntity pitchEntity = pitchMapper.mapCreatePitchRequest(createPitchRequest, campaignEntity, userId);
        pitchRepository.save(pitchEntity);
    }

    public void deletePitch(Integer id, String xUserId) {
        PitchEntity pitchEntity = pitchRepository.findById(id.longValue()).orElseThrow(() -> PITCH_NOT_FOUND.toException(id.toString()));

        if(!pitchEntity.getOwnerId().equals(xUserId) || !ALLOW_PITCH_DELETE_STATES.contains(pitchEntity.getState()))
            ErrorCode.NOT_AUTHORISED_TO_DELETE.throwException(id.toString());

        pitchEntity.getCampaign().getPitches().remove(pitchEntity);
        pitchRepository.delete(pitchEntity);

        campaignService.setCampaignStatusBaseOnDeletedPitchState(pitchEntity.getState(), pitchEntity.getCampaign());
    }

    public void updatePitchState(Integer id, String xUserId, String pitchState) {
        PitchEntity pitchEntity = pitchRepository.findById(id.longValue()).orElseThrow(() -> PITCH_NOT_FOUND.toException(id.toString()));

        if(!pitchEntity.getCampaign().getOwnerId().equals(xUserId) && !pitchEntity.getOwnerId().equals(xUserId))
            ErrorCode.NOT_AUTHORISED_TO_UPDATE.throwException(id.toString());

        // Check if campaign owner tries to change state
        if(
            pitchEntity.getCampaign().getOwnerId().equals(xUserId) &&
            !otherPitchesAreAllPending(pitchEntity) &&
            !isPitchStateUpdateValidForCampaignOwner(pitchEntity.getState(), PitchState.valueOf(pitchState))
        ) {
            ErrorCode.INVALID_STATE_CHANGE.throwException(pitchEntity.getId().toString(), pitchEntity.getState().name(), PitchState.valueOf(pitchState).name());
        }

        // Check if pitch owner tries to change state
        if(
            pitchEntity.getOwnerId().equals(xUserId) &&
            !isPitchStateUpdateValidForPitchOwner(pitchEntity.getState(), PitchState.valueOf(pitchState))
        ) {
            ErrorCode.INVALID_STATE_CHANGE.throwException(pitchEntity.getId().toString(), pitchEntity.getState().name(), PitchState.valueOf(pitchState).name());
        }

        pitchEntity.setState(PitchState.valueOf(pitchState));
        campaignService.updateCampaignBasedOnPitchStateChange(PitchState.valueOf(pitchState), pitchEntity.getCampaign());
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

    private boolean isPitchStateUpdateValidForCampaignOwner(PitchState oldState, PitchState newState) {
        return CAMPAIGN_OWNER_ALLOWED_PITCH_STATE_TRANSITIONS.get(oldState).contains(newState);
    }

    private boolean isPitchStateUpdateValidForPitchOwner(PitchState oldState, PitchState newState) {
        return PITCH_OWNER_ALLOWED_PITCH_STATE_TRANSITIONS.get(oldState).contains(newState);
    }

    private boolean otherPitchesAreAllPending(PitchEntity pitch) {
        return pitch.getCampaign().getPitches().stream().filter(p -> !p.equals(pitch)).allMatch(otherPitch -> otherPitch.getState().equals(PitchState.PENDING));
    }

}
