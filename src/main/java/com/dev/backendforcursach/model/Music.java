package com.dev.backendforcursach.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "musics")
@AllArgsConstructor
@NoArgsConstructor
public class Music {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "album_name")
    private String albumName;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "count")
    private Integer count;

    @Column(name = "description")
    private String description;

    @Column(name = "release_date")
    private LocalDate releaseDate;
    
    @CreationTimestamp
    @Column(name = "date_of_update")
    private LocalDate dateOfUpdate;

    @Column(name = "img_url")
    private String imgUrl;

    @Column(name = "song_url")
    private String songUrl;

    @Column(name = "test_song_name")
    private String testSongName;
}
