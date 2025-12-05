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
        if (campaignEntity.getOwnerId().equals(ownerId)) {
            campaignRepository.delete(campaignEntity);
        } else {
            ErrorCode.NOT_AUTHORISED_TO_DELETE.throwException(id.toString());
        }
    }

    public void setCampaignStatusBaseOnPitchStateChange(PitchState pitchState, CampaignEntity campaign) {
        switch (pitchState) {
            case PitchState.PENDING -> campaign.setState(CampaignState.PENDING);
            case PitchState.SELECTED -> campaign.setState(CampaignState.PITCH_SELECTED);
            case PitchState.ACCEPTED -> campaign.setState(CampaignState.PITCH_ACCEPTED);
            case PitchState.DONE -> campaign.setState(CampaignState.DONE);
        }
    }

    // TODO
    //    private Boolean isFavorited(CampaignEntity campaignEntity, String userId) {
    //        if(Objects.isNull(userId))
    //            return null;
    //        return campaignEntity.getFavoritedBy().contains(userId);
    //    }
}
