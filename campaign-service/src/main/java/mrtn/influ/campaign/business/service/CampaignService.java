package mrtn.influ.campaign.business.service;

import mrtn.influ.campaign.dao.CampaignRepository;
import mrtn.influ.campaign.dto.CampaignDto;
import mrtn.influ.campaign.dto.CreateCampaignRequest;
import mrtn.influ.campaign.entity.CampaignEntity;
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

    public List<CampaignDto> getAllCampaign() {
        return campaignRepository.findAll().stream().map(campaignEntity -> campaignMapper.mapCampaign(campaignEntity, null)).toList();
    }

    public CampaignDto getCampaignById(Long id, String userId) {
        CampaignEntity campaignEntity = campaignRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find campaign for id: %d".formatted(id)));
        return campaignMapper.mapCampaign(campaignEntity, isFavorited(campaignEntity, userId));
    }

    public List<CampaignDto> getCampaignsForUser(String userId) {
        List<CampaignEntity> campaignEntities = campaignRepository.findAllByUserId(userId);
        return campaignEntities.stream().map(campaignEntity -> campaignMapper.mapCampaign(campaignEntity, isFavorited(campaignEntity, userId))).toList();
    }

    public List<CampaignDto> getSavedCampaignsForUser(String userId) {
        List<CampaignEntity> campaignEntities = campaignRepository.findSavedByUserId(userId);
        return campaignEntities.stream().map(campaignEntity -> campaignMapper.mapCampaign(campaignEntity, isFavorited(campaignEntity, userId))).toList();
    }

    public void saveCampaign(CreateCampaignRequest createCampaignRequest, String userId) {
        CampaignEntity campaignEntity = campaignMapper.mapCreateCampaignRequest(createCampaignRequest, userId);
        campaignRepository.save(campaignEntity);
    }

    private Boolean isFavorited(CampaignEntity campaignEntity, String userId) {
        if(Objects.isNull(userId))
            return null;
        return campaignEntity.getFavoritedBy().contains(userId);
    }
}
