package com.dev.backendforcursach.mapper;

import com.dev.backendforcursach.enums.Role;
import com.dev.backendforcursach.model.User;
import com.dev.backendforcursach.model.dto.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", imports = Role.class)
public abstract class AdminMapper {

  @Autowired
  PasswordEncoder passwordEncoder;

  @Mapping(target = "password", expression = "java( passwordEncoder.encode(userRequest.getPassword()))")
  @Mapping(target = "role", expression = "java(Role.ADMIN)")
  public abstract User toUser(UserRequest userRequest);
}
