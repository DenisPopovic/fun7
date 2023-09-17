package com.outfit7.fun7.service.admin;

import com.outfit7.fun7.model.database.Fun7User;
import com.outfit7.fun7.model.dto.database.Fun7UserDto;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

public interface AdminService {
  List<Fun7UserDto> getAllUsers();

  void deleteUser(String token, UUID userUUID);

  Fun7User getUser(UUID userUUID) throws EntityNotFoundException;
}
