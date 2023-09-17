package com.outfit7.fun7.persistence.impl;

import com.outfit7.fun7.model.database.UserInfo;
import com.outfit7.fun7.persistence.UserInfoPersistenceService;
import com.outfit7.fun7.persistence.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserInfoPersistenceServiceImpl implements UserInfoPersistenceService {

  private final UserInfoRepository userInfoRepository;
  private final String USER_INFO_NOT_EXIST =
      "User Info for user with email '%s' does not exist. Please try to log in again.";

  @Override
  public long getLoginCount(String email) throws EntityNotFoundException {

    Optional<UserInfo> optionalUserInfo = userInfoRepository.findUserInfoByUserEmail(email);
    if (optionalUserInfo.isPresent()) {
      UserInfo userInfo = optionalUserInfo.get();
      return userInfo.getLoginCount();
    } else {
      throw new EntityNotFoundException(USER_INFO_NOT_EXIST.formatted(email));
    }
  }

  @Override
  public void updateUserInfo(String email) throws EntityNotFoundException {

    Optional<UserInfo> userInfoOptional = userInfoRepository.findUserInfoByUserEmail(email);
    if (userInfoOptional.isPresent()) {
      UserInfo userInfo = userInfoOptional.get();
      userInfo.setLoginCount(userInfo.getLoginCount() + 1);
      userInfoRepository.save(userInfo);
    } else {
      throw new EntityNotFoundException(USER_INFO_NOT_EXIST.formatted(email));
    }
  }
}
