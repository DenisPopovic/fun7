package com.outfit7.fun7.persistence.repository;

import com.outfit7.fun7.model.database.Fun7User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Fun7UserRepository extends JpaRepository<Fun7User, UUID> {

  @Query("SELECT u FROM Fun7User u WHERE u.email = ?1")
  Optional<Fun7User> findUserByEmail(String email);
}
