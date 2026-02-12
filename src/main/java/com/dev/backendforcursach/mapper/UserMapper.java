package com.dev.backendforcursach.mapper;

import com.dev.backendforcursach.enums.Role;
import com.dev.backendforcursach.model.User;
import com.dev.backendforcursach.model.dto.UserDto;
import com.dev.backendforcursach.model.dto.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", imports = {Role.class, java.math.BigDecimal.class})
public abstract class UserMapper {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Mapping(target = "balance", expression = "java(new BigDecimal(0))")
  @Mapping(target = "role", expression = "java(Role.USER)")
  @Mapping(target = "password", expression = "java(passwordEncoder.encode(userRequest.getPassword()))")
  public abstract User toUser(UserRequest userRequest);

  @Mapping(target = "role", expression = "java(user.getRole().toString())")
  public abstract UserDto userToUserDto(User user);
}
