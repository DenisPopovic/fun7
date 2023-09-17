package com.outfit7.fun7.persistence;

import com.outfit7.fun7.model.database.Fun7User;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.springframework.security.core.userdetails.User;

public interface Fun7UserPersistenceService {
  Fun7User getFun7UserByUUID(UUID userUUID) throws EntityNotFoundException;

  Fun7User getFun7UserByEmail(String email) throws EntityNotFoundException;

  List<Fun7User> getAllFun7Users();

  void deleteFun7User(UUID userUUID) throws EntityNotFoundException;

  User getUserByEmail(String email) throws EntityNotFoundException;
}
