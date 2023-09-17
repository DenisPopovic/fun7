package com.outfit7.fun7.mapper;

import com.outfit7.fun7.model.database.Fun7User;
import com.outfit7.fun7.model.dto.database.Fun7UserDto;
import java.util.function.Function;
import org.springframework.stereotype.Service;

@Service
public class Fun7UserDtoMapper implements Function<Fun7User, Fun7UserDto> {
  @Override
  public Fun7UserDto apply(Fun7User fun7User) {
    return new Fun7UserDto(fun7User.getUuid(), fun7User.getEmail(), fun7User.getRole());
  }
}
