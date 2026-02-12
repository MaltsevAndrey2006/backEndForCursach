package andrey.dev.backendforcursach.dto.mapper;

import andrey.dev.backendforcursach.dto.UserRequest;
import andrey.dev.backendforcursach.enums.Role;
import andrey.dev.backendforcursach.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring", imports = {Role.class, java.math.BigDecimal.class})
public interface UserMapper {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Mapping(target = "balance", expression = "java(new BigDecimal(0))")
    @Mapping(target = "role", expression = "java(Role.USER)")
    @Mapping(target = "password", expression = "java( passwordEncoder.encode(userRequest.getPassword()))")
    User toUser(UserRequest userRequest);
}
