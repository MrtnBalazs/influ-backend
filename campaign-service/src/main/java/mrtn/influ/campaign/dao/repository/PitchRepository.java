package mrtn.influ.campaign.dao.repository;

import mrtn.influ.campaign.dao.entity.PitchEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitchRepository extends ListCrudRepository<PitchEntity, Long> {
    List<PitchEntity> findByOwnerId(String ownerId);
}
