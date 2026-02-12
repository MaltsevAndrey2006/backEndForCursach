package com.dev.backendforcursach.mapper;
import com.dev.backendforcursach.model.Music;
import com.dev.backendforcursach.model.dto.MusicRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    Music toMusic(MusicRequest musicRequest);
}