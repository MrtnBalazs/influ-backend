package mrtn.influ.dao;

import mrtn.influ.entity.CampaignEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends ListCrudRepository<CampaignEntity, Long> {
    List<CampaignEntity> findAllByUserId(String userId);
}
