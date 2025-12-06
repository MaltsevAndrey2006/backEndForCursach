package andrey.dev.backendforcursach.dto.mapper;

import andrey.dev.backendforcursach.dto.MusicRequest;
import andrey.dev.backendforcursach.models.Music;
import andrey.dev.backendforcursach.service.FileStorageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MusicMapper {
    @Mapping(target = "imgUrl", expression = "java(fileStorageService.uploadImage(musicRequest.getImg(),musicRequest.getAlbumName()))")
    @Mapping(target = "songUrl", expression = "java(fileStorageService.uploadMusic(musicRequest.getSong(),musicRequest.getTestSongName()))")
    Music toMusic(MusicRequest musicRequest, FileStorageService fileStorageService);
}
