package com.dev.backendforcursach.mapper;

import com.dev.backendforcursach.enums.FileType;
import com.dev.backendforcursach.model.Music;
import com.dev.backendforcursach.model.dto.MusicRequest;
import com.dev.backendforcursach.service.FileStorageService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", imports = FileType.class)
public abstract class MusicMapper {
  @Autowired
  FileStorageService fileStorageService;

  @Mapping(target = "imgUrl", expression = "java(fileStorageService.uploadFile(musicRequest.getImg(),musicRequest.getAlbumName(), FileType.IMAGE))")
  @Mapping(target = "songUrl", expression = "java(fileStorageService.uploadFile(musicRequest.getSong(),musicRequest.getTestSongName(), FileType.SONG))")
  public abstract Music toMusic(MusicRequest musicRequest);
}