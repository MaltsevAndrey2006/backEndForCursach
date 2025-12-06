package andrey.dev.backendforcursach.repositores;

import andrey.dev.backendforcursach.models.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
}
