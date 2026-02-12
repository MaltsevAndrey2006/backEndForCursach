package com.dev.backendforcursach.security;

import com.dev.backendforcursach.enums.Role;
import com.dev.backendforcursach.model.User;
import io.micrometer.common.util.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(User user) implements UserDetails {
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    var role = user.getRole().toString();

    if (StringUtils.isEmpty(role)) {
      return List.of(new SimpleGrantedAuthority(Role.USER.name()));
    }

    var userRole = Role.fromString(role);
    return List.of(new SimpleGrantedAuthority(userRole.name()));
  }


  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getLogin();
  }
}
