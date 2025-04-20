package mrtn.influ.dao;

import mrtn.influ.entity.PitchEntity;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitchRepository extends ListCrudRepository<PitchEntity, Long> {
    List<PitchEntity> findByCreatorId(String userId);
}
