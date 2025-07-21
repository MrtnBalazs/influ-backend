package mrtn.influ.campaign.service;

import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.entity.PitchEntity;
import mrtn.influ.campaign.dao.repository.CampaignRepository;
import mrtn.influ.campaign.dao.repository.PitchRepository;
import mrtn.influ.campaign.dto.CreatePitchRequest;
import mrtn.influ.campaign.dto.Pitch;
import mrtn.influ.campaign.mapper.PitchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PitchService {

    @Autowired
    private PitchRepository pitchRepository;
    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private PitchMapper pitchMapper;

    public void createPitch(CreatePitchRequest createPitchRequest, String userId) {
        // TODO validation rq
        // TODO exception handling
        CampaignEntity campaignEntity = campaignRepository
                .findById(createPitchRequest.getCampaignId().longValue())
                .orElseThrow(() -> new RuntimeException("Could not find campaign with id:%d".formatted(createPitchRequest.getCampaignId())));

        PitchEntity pitchEntity = pitchMapper.mapCreatePitchRequest(createPitchRequest, campaignEntity, userId);
        pitchRepository.save(pitchEntity);
    }

    public void deletePitch(Integer id, String xUserId) {
        // TODO exception handling
        PitchEntity pitchEntity = pitchRepository.findById(id.longValue()).orElseThrow(() -> new RuntimeException());
        if(!pitchEntity.getOwnerId().equals(xUserId))
            throw new RuntimeException();
        pitchRepository.delete(pitchEntity);
    }

    public List<Pitch> getPitchesForUser(String userId) {
        List<PitchEntity> pitchEntities = pitchRepository.findByOwnerId(userId);
        return pitchMapper.mapPitchEntities(pitchEntities);
    }
}
