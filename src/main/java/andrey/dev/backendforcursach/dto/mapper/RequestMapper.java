package andrey.dev.backendforcursach.dto.mapper;

import andrey.dev.backendforcursach.dto.MusicRequest;
import andrey.dev.backendforcursach.models.Music;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    Music toMusic(MusicRequest musicRequest);
}
