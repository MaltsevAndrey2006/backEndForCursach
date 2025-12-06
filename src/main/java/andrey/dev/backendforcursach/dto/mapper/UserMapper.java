package andrey.dev.backendforcursach.dto.mapper;

import andrey.dev.backendforcursach.dto.UserRequest;
import andrey.dev.backendforcursach.enums.Role;
import andrey.dev.backendforcursach.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",imports = {Role.class,  java.math.BigDecimal.class } )
public interface UserMapper {
    @Mapping(target = "balance", expression = "java(new BigDecimal(0))")
    @Mapping(target = "role", expression = "java(Role.USER)")
    User toUser(UserRequest userRequest);
}
