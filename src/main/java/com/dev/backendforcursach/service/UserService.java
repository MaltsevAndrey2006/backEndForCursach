package com.dev.backendforcursach.service;

import com.dev.backendforcursach.exception.InvalidCredentialsException;
import com.dev.backendforcursach.mapper.AdminMapper;
import com.dev.backendforcursach.mapper.UserMapper;
import com.dev.backendforcursach.model.User;
import com.dev.backendforcursach.model.dto.JwtAuthenticationDto;
import com.dev.backendforcursach.model.model.dto.UserCredentialsDto;
import com.dev.backendforcursach.model.dto.UserDto;
import com.dev.backendforcursach.model.dto.UserRequest;
import com.dev.backendforcursach.repository.UserRepository;
import com.dev.backendforcursach.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationDto login(UserCredentialsDto userCredentialsDto) {
        var user = findByCredentials(userCredentialsDto.getLogin(), userCredentialsDto.getPassword());
        return jwtService.generateJwtAuthToken(user.getLogin());
    }

    private User findByCredentials(String login, String password) {
        if (Objects.isNull(login) ||
                Objects.isNull(password)) {
            log.warn("Attempted login with null credentials");
            throw new InvalidCredentialsException("Credentials cannot be null");
        }

        var user = userRepository.findByLogin(login)
                .orElseThrow(() -> {
                    log.warn("Login attempt failed: user not found for login {}", login);
                    return new InvalidCredentialsException("Invalid login or password");
                });

        if (!passwordEncoder.matches(password, user.getPassword())) {
            log.warn("Login attempt failed: invalid password for login {}", login);
            throw new InvalidCredentialsException("Invalid login or password");
        }
        return user;
    }

    public UserDto findByLogin(String login) {
        return userRepository.findByLogin(login)
                .map(userMapper::userToUserDto)
                .orElseThrow(() -> new RuntimeException(String.format("Such login: %s doesnt exist", login)));
    }

    public void createUser(UserRequest userRequest) {
        userRepository.save(userMapper.toUser(userRequest));
    }

    public void createAdmin(UserRequest userRequest) {
        userRepository.save(adminMapper.toUser(userRequest));
    }

    public UserDto getUserById(Long id) {
        return userMapper.userToUserDto(userRepository.findById(id).orElseThrow(() -> new RuntimeException("no such user with this id")));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserDto)
                .toList();
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void updateUser(BigDecimal balance, String address, Long id) {
        userRepository.updateUser(id, balance, address);
    }

    @Transactional
    public void changeBalance(Long id, BigDecimal balance) {
        userRepository.changeBalance(id, balance);
    }

    public List<UserDto> getAllAdmins() {
        return userRepository.findAdmins().stream().map(userMapper::userToUserDto).toList();
    }

    public List<UserDto> getAllUsersExceptAdmin(Long id) {
        return userRepository.findAllUsersExceptAdmin(id)
                .stream()
                .map(userMapper::userToUserDto)
                .toList();
    }
}