package com.dev.backendforcursach.repository;

import com.dev.backendforcursach.model.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MusicRepository extends JpaRepository<Music, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Music m SET " +
            "m.count = :#{#music.count}, " +
            "m.price = :#{#music.price}, " +
            "m.groupName = :#{#music.groupName}, " +
            "m.albumName = :#{#music.albumName}, " +
            "m.description = :#{#music.description}, " +
            "m.imgUrl = :#{#music.imgUrl}, " +
            "m.releaseDate = :#{#music.releaseDate}, " +
            "m.songUrl = :#{#music.songUrl}, " +
            "m.testSongName = :#{#music.testSongName}, " +
            "m.dateOfUpdate = current_date " +
            "WHERE m.id = :id")
    void updateMusic(@Param("music") Music music, @Param("id") Long id);

    @Modifying
    @Query("UPDATE Music  m SET m.count = m.count - :count WHERE m.id = :id")
    void changeCount(@Param("id") Long id, @Param("count") Integer count);

    @Query(value = "SELECT m FROM Music m ORDER BY m.price")
    List<Music> findAllSortedByPriceABSMusics();

    @Query(value = "SELECT m FROM Music m ORDER BY m.price DESC ")
    List<Music> findAllSortedByPriceDESCMusics();

    @Query(value = "SELECT m FROM Music m ORDER BY m.releaseDate ")
    List<Music> findAllSortedByReleaseDateABSMusics();

    @Query(value = "SELECT m FROM Music m ORDER BY m.releaseDate DESC")
    List<Music> findAllSortedByReleaseDateDESCMusics();

    @Query(value = "SELECT m FROM Music m WHERE ( LOWER(m.albumName) LIKE LOWER(CONCAT('%', :search, '%')) ) OR ( LOWER(m.groupName) LIKE LOWER(CONCAT('%', :search, '%'))) OR (LOWER(CAST(m.releaseDate AS string )) LIKE LOWER(CONCAT('%', :search, '%')))")
    List<Music> findAllMusicsWithSearch(@Param("search") String search);

}
