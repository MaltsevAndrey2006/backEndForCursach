package andrey.dev.backendforcursach.service;

import andrey.dev.backendforcursach.dto.UserDto;
import andrey.dev.backendforcursach.dto.UserRequest;
import andrey.dev.backendforcursach.dto.mapper.AdminMapper;
import andrey.dev.backendforcursach.dto.mapper.UserDtoMapper;
import andrey.dev.backendforcursach.dto.mapper.UserMapper;
import andrey.dev.backendforcursach.repositores.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AdminMapper adminMapper;
    private final UserDtoMapper userDtoMapper;

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


}
