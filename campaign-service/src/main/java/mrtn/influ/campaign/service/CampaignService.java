package mrtn.influ.campaign.service;

import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.entity.CampaignState;
import mrtn.influ.campaign.dao.entity.PitchState;
import mrtn.influ.campaign.dao.repository.CampaignRepository;
import mrtn.influ.campaign.dto.Campaign;
import mrtn.influ.campaign.exception.ErrorCode;
import mrtn.influ.campaign.mapper.CampaignMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CampaignService {

    @Autowired
    private CampaignRepository campaignRepository;
    @Autowired
    private CampaignMapper campaignMapper;

    public List<Campaign> getAllCampaign() {
        return campaignRepository.findAll().stream().map(campaignEntity -> campaignMapper.mapCampaign(campaignEntity)).toList();
    }

    public Campaign getCampaignById(Long id) {
        CampaignEntity campaignEntity = campaignRepository.findById(id).orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.toException(id.toString()));
        return campaignMapper.mapCampaign(campaignEntity);
    }

    public List<Campaign> getCampaignsForUser(String userId) {
        List<CampaignEntity> campaignEntities = campaignRepository.findAllByOwnerId(userId);
        return campaignEntities.stream().map(campaignEntity -> campaignMapper.mapCampaign(campaignEntity)).toList();
    }

    public void saveCampaign(mrtn.influ.campaign.dto.CreateCampaignRequest createCampaignRequest, String ownerId) {
        CampaignEntity campaignEntity = campaignMapper.mapCreateCampaignRequest(createCampaignRequest, ownerId);
        campaignRepository.save(campaignEntity);
    }

    public void deleteCampaign(Integer id, String ownerId) {
        CampaignEntity campaignEntity = campaignRepository.findById(id.longValue()).orElseThrow(() -> ErrorCode.CAMPAIGN_NOT_FOUND.toException(id.toString()));
        if (campaignEntity.getOwnerId().equals(ownerId) && (campaignEntity.getState() == CampaignState.PENDING || campaignEntity.getState() == CampaignState.PITCH_SELECTED)) {
            campaignEntity.getPitches().forEach(pitchEntity ->
            {
                pitchEntity.setCampaign(null);
            });
            campaignRepository.delete(campaignEntity);
        } else {
            ErrorCode.NOT_AUTHORISED_TO_DELETE.throwException(id.toString());
        }
    }

    public void updateCampaignBasedOnPitchStateChange(PitchState pitchState, CampaignEntity campaign) {
        switch (pitchState) {
            case PitchState.PENDING -> campaign.setState(CampaignState.PENDING);
            case PitchState.SELECTED -> campaign.setState(CampaignState.PITCH_SELECTED);
            case PitchState.ACCEPTED -> {
                campaign.setState(CampaignState.PITCH_ACCEPTED);
                campaign.getPitches().forEach(pitchEntity -> {
                    if(pitchEntity.getState() != PitchState.ACCEPTED) {
                        pitchEntity.setState(PitchState.REJECTED);
                    }
                });
            }
            case PitchState.ABORTED -> campaign.setState(CampaignState.ABORTED);
            case PitchState.DONE -> campaign.setState(CampaignState.DONE);
        }
    }

    public void setCampaignStatusBaseOnDeletedPitchState(PitchState pitchState, CampaignEntity campaign) {
        if (Objects.requireNonNull(pitchState) == PitchState.SELECTED) {
            campaign.setState(CampaignState.PENDING);
        }
    }

    // TODO
    //    private Boolean isFavorited(CampaignEntity campaignEntity, String userId) {
    //        if(Objects.isNull(userId))
    //            return null;
    //        return campaignEntity.getFavoritedBy().contains(userId);
    //    }
}
