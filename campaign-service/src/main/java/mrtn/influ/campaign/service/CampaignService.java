package mrtn.influ.campaign.service;

import mrtn.influ.campaign.dao.entity.CampaignEntity;
import mrtn.influ.campaign.dao.repository.CampaignRepository;
import mrtn.influ.campaign.dto.Campaign;
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
        // TODO better exception handling
        CampaignEntity campaignEntity = campaignRepository.findById(id).orElseThrow(() -> new RuntimeException("Could not find campaign for id: %d".formatted(id)));
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
        // TODO validate rq
        CampaignEntity campaignEntity = campaignRepository.findById(id.longValue()).orElseThrow(() -> {
            // TODO not found exception
            return new RuntimeException();
        });
        if (campaignEntity.getOwnerId().equals(ownerId)) {
            campaignRepository.delete(campaignEntity);
        } else {
            // TODO authorization exception
        }
    }

    // TODO
    //    private Boolean isFavorited(CampaignEntity campaignEntity, String userId) {
    //        if(Objects.isNull(userId))
    //            return null;
    //        return campaignEntity.getFavoritedBy().contains(userId);
    //    }
}
