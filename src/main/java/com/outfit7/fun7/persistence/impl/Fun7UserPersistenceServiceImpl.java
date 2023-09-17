package com.outfit7.fun7.persistence.impl;

import com.outfit7.fun7.mapper.UserMapper;
import com.outfit7.fun7.model.database.Fun7User;
import com.outfit7.fun7.persistence.Fun7UserPersistenceService;
import com.outfit7.fun7.persistence.repository.Fun7UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Fun7UserPersistenceServiceImpl implements Fun7UserPersistenceService {
  private final Fun7UserRepository fun7UserRepository;
  private final UserMapper userMapper;
  private final String USER_ID_NOT_FOUND = "User with id '%s' not found.";
  private final String USER_EMAIL_NOT_FOUND = "User with email '%s' not found.";

  @Override
  public Fun7User getFun7UserByUUID(UUID userUUID) throws EntityNotFoundException {
    return fun7UserRepository
        .findById(userUUID)
        .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOT_FOUND.formatted(userUUID)));
  }

  @Override
  public Fun7User getFun7UserByEmail(String email) throws EntityNotFoundException {
    return fun7UserRepository
        .findUserByEmail(email)
        .orElseThrow(() -> new EntityNotFoundException(USER_EMAIL_NOT_FOUND.formatted(email)));
  }

  @Override
  public List<Fun7User> getAllFun7Users() {
    return fun7UserRepository.findAll();
  }

  @Override
  public void deleteFun7User(UUID userUUID) throws EntityNotFoundException {
    if (!fun7UserRepository.existsById(userUUID)) {
      throw new EntityNotFoundException(USER_ID_NOT_FOUND.formatted(userUUID));
    }
    fun7UserRepository.deleteById(userUUID);
  }

  @Override
  public User getUserByEmail(String email) throws EntityNotFoundException {
    return fun7UserRepository
        .findUserByEmail(email)
        .map(userMapper)
        .orElseThrow(() -> new EntityNotFoundException(USER_ID_NOT_FOUND.formatted(email)));
  }
}
