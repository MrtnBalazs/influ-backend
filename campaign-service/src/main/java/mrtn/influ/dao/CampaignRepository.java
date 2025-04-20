package mrtn.influ.dao;

import mrtn.influ.entity.CampaignEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends ListCrudRepository<CampaignEntity, Long> {
    List<CampaignEntity> findAllByUserId(String userId);

    @Query(value = "select * from CAMPAIGN c inner join FAVORITED_BYS f on f.CAMPAIGN_ID=c.ID where f.FAVORITED_BY = :userId", nativeQuery = true)
    List<CampaignEntity> findSavedByUserId(@Param("userId") String userId);
}
