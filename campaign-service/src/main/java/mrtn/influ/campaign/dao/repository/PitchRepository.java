package mrtn.influ.campaign.dao.repository;

import mrtn.influ.campaign.dao.entity.PitchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PitchRepository extends JpaRepository<PitchEntity, Long> {
    List<PitchEntity> findByOwnerId(String ownerId);
}
