package com.dev.backendforcursach.security;

import com.dev.backendforcursach.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {
  private final UserRepository userRepository;

  @Override
  public CustomUserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
    return userRepository.findByLogin(login)
        .map(CustomUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException(login));
  }
}
