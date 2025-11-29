package mrtn.influ.user.dao.repository;

import mrtn.influ.user.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findUserByEmail(String email);
    Optional<UserEntity> findUserByUsername(String username);
}
