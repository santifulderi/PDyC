package ar.edu.unnoba.pdyc2024.mymusic.repository;

import ar.edu.unnoba.pdyc2024.mymusic.model.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
