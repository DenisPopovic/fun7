package com.outfit7.fun7.persistence.repository;

import com.outfit7.fun7.model.database.UserInfo;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, UUID> {
  @Query("SELECT ui FROM UserInfo ui WHERE ui.fun7User.uuid = ?1")
  Optional<UserInfo> findUserInfoByUserUUID(UUID uuid);

  @Query("SELECT ui FROM UserInfo ui WHERE ui.fun7User.email = ?1")
  Optional<UserInfo> findUserInfoByUserEmail(String email);
}
