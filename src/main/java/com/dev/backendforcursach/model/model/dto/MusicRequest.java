package com.dev.backendforcursach.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MusicRequest {

    private String albumName;

    private String groupName;

    private BigDecimal price;

    private Integer count;

    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    private MultipartFile img;

    private MultipartFile song;

    private String testSongName;

}
