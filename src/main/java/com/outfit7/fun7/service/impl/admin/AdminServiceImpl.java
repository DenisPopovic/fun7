package com.outfit7.fun7.service.impl.admin;

import com.outfit7.fun7.mapper.Fun7UserDtoMapper;
import com.outfit7.fun7.model.database.Fun7User;
import com.outfit7.fun7.model.dto.database.Fun7UserDto;
import com.outfit7.fun7.persistence.Fun7UserPersistenceService;
import com.outfit7.fun7.service.admin.AdminService;
import com.outfit7.fun7.util.AuthUtils;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  public final Fun7UserPersistenceService fun7UserPersistenceService;
  public final Fun7UserDtoMapper fun7UserDtoMapper;
  private final AuthUtils authUtils;

  @Override
  public List<Fun7UserDto> getAllUsers() {
    return fun7UserPersistenceService.getAllFun7Users().stream()
        .map(fun7UserDtoMapper)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteUser(String token, UUID userUUID) {
    String email = authUtils.extractEmail(token);
    Fun7User caller = fun7UserPersistenceService.getFun7UserByEmail(email);
    if (caller.getUuid().equals(userUUID)) {
      throw new IllegalArgumentException("You cannot delete your own account.");
    }

    fun7UserPersistenceService.deleteFun7User(userUUID);
  }

  @Override
  public Fun7User getUser(UUID userUUID) throws EntityNotFoundException {
    return fun7UserPersistenceService.getFun7UserByUUID(userUUID);
  }
}
