package mrtn.influ.user.dao;

import mrtn.influ.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findUserByEmail(String email);
}
