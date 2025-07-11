package mrtn.influ.campaign.business.service;

import mrtn.influ.campaign.dao.CampaignRepository;
import mrtn.influ.campaign.dao.PitchRepository;
import mrtn.influ.campaign.dto.CreatePitchRequest;
import mrtn.influ.campaign.dto.PitchDto;
import mrtn.influ.campaign.entity.CampaignEntity;
import mrtn.influ.campaign.entity.PitchEntity;
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

    public PitchDto getPitchById(Long id) {
        return pitchMapper
                .mapPitchEntity(pitchRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Could not find pitch with id:%d".formatted(id))));
    }

    public List<PitchDto> getPitchesByUserId(String userId) {
        return pitchRepository
                .findByCreatorId(userId)
                .stream()
                .map(pitchEntity -> pitchMapper.mapPitchEntity(pitchEntity)).toList();
    }

    public void createPitch(CreatePitchRequest createPitchRequest, String userId) {
        CampaignEntity campaignEntity = campaignRepository
                .findById(createPitchRequest.getCampaignId())
                .orElseThrow(() -> new RuntimeException("Could not find campaign with id:%d".formatted(createPitchRequest.getCampaignId())));
        PitchEntity pitchEntity = pitchMapper.mapCreatePitchRequest(createPitchRequest, campaignEntity, userId);
        pitchRepository.save(pitchEntity);
    }
}
