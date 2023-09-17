package com.outfit7.fun7.persistence;

import jakarta.persistence.EntityNotFoundException;

public interface UserInfoPersistenceService {
  long getLoginCount(String email) throws EntityNotFoundException;

  void updateUserInfo(String email) throws EntityNotFoundException;
}
