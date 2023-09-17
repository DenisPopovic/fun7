package com.outfit7.fun7.mapper;

import com.outfit7.fun7.model.database.Fun7User;
import java.util.Collections;
import java.util.function.Function;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements Function<Fun7User, User> {

  @Override
  public User apply(Fun7User fun7User) {
    return new User(
        fun7User.getEmail(),
        fun7User.getPassword(),
        Collections.singletonList(new SimpleGrantedAuthority(fun7User.getRole().getKey())));
  }
}
