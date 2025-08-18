package mrtn.influ.campaign.service;

import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.entity.PitchEntity;
import mrtn.influ.campaign.dao.repository.CampaignRepository;
import mrtn.influ.campaign.dao.repository.PitchRepository;
import mrtn.influ.campaign.dto.CreatePitchRequest;
import mrtn.influ.campaign.dto.Pitch;
import mrtn.influ.campaign.exception.ErrorCode;
import mrtn.influ.campaign.mapper.PitchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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

    public void createPitch(CreatePitchRequest createPitchRequest, String userId) {
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

    public List<Pitch> getPitchesForUser(String userId) {
        List<PitchEntity> pitchEntities = pitchRepository.findByOwnerId(userId);
        return pitchMapper.mapPitchEntities(pitchEntities);
    }

    public Pitch getPitch(String userId, Integer pitchId) {
        PitchEntity pitchEntity = pitchRepository.findById(pitchId).orElseThrow(() -> PITCH_NOT_FOUND.toException(pitchId.toString()));
        if(!pitchEntity.getOwnerId().equals(userId)) {
            NOT_AUTHORISED_TO_GET.throwException(userId);
        }
        return pitchMapper.mapPitchEntity(pitchEntity);
    }
}
