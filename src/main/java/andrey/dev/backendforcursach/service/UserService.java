package andrey.dev.backendforcursach.service;

import andrey.dev.backendforcursach.dto.JwtAuthenticationDto;
import andrey.dev.backendforcursach.dto.UserCredentialsDto;
import andrey.dev.backendforcursach.dto.UserDto;
import andrey.dev.backendforcursach.dto.UserRequest;
import andrey.dev.backendforcursach.dto.mapper.AdminMapper;
import andrey.dev.backendforcursach.dto.mapper.UserDtoMapper;
import andrey.dev.backendforcursach.dto.mapper.UserMapper;
import andrey.dev.backendforcursach.models.User;
import andrey.dev.backendforcursach.repositores.UserRepository;
import andrey.dev.backendforcursach.security.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final UserDtoMapper userDtoMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public JwtAuthenticationDto signIn(UserCredentialsDto userCredentialsDto) {
        return jwtService.generateJwtAuthToken(findByCredentials(userCredentialsDto).getLogin());
    }

    private User findByCredentials(UserCredentialsDto userCredentialsDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userCredentialsDto.getLogin());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(userCredentialsDto.getPassword(), user.getPassword())) {
                return user;
            }

        }
        throw new RuntimeException(String.format("Password: %s inCorrect"
                , userCredentialsDto.getPassword()));
    }

    public UserDto findByLogin(String login) {
        return userRepository.findByLogin(login).map(userDtoMapper::userToUserDto)
                .orElseThrow(() -> new RuntimeException(String.format("Such login: %s doesnt exist", login)));
    }

    public void createUser(UserRequest userRequest) {
        userRepository.save(userMapper.toUser(userRequest));
    }

    public void createAdmin(UserRequest userRequest) {
        userRepository.save(adminMapper.toUser(userRequest));
    }

    public UserDto getUserById(Long id) {
        return userDtoMapper.userToUserDto(userRepository.findById(id).orElseThrow(() -> new RuntimeException("no such user with this id")));
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userDtoMapper::userToUserDto).toList();
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
        return userRepository.findAdmins().stream().map(userDtoMapper::userToUserDto).toList();
    }

    public List<UserDto> getAllUsersExceptAdmin(Long id) {
        return userRepository.findAllUsersExceptAdmin(id).stream().map(userDtoMapper::userToUserDto).toList();
    }
}
