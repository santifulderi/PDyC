package ar.edu.unnoba.pdyc2024.mymusic.repository;

import ar.edu.unnoba.pdyc2024.mymusic.model.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}
