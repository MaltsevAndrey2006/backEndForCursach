package andrey.dev.backendforcursach.dto.mapper;

import andrey.dev.backendforcursach.dto.UserRequest;
import andrey.dev.backendforcursach.enums.Role;
import andrey.dev.backendforcursach.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", imports = Role.class)
public interface AdminMapper {

    @Mapping(target = "role", expression = "java(Role.ADMIN)")
    User toUser(UserRequest userRequest);
}
