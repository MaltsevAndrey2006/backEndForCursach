package andrey.dev.backendforcursach.dto.mapper;

import andrey.dev.backendforcursach.dto.UserDto;
import andrey.dev.backendforcursach.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "role", expression = "java(user.getRole().toString())")
    UserDto userToUserDto(User user);
}
