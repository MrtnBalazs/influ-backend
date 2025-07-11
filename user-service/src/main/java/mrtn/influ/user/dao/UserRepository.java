package mrtn.influ.user.dao;

import mrtn.influ.user.dto.UserType;
import mrtn.influ.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findUserByUsername(String username);
    List<UserEntity> findUserByUserType(UserType userType);
}
