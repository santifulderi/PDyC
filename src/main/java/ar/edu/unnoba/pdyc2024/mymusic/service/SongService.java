package ar.edu.unnoba.pdyc2024.mymusic.service;

import ar.edu.unnoba.pdyc2024.mymusic.model.Song;
import ar.edu.unnoba.pdyc2024.mymusic.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SongService {

    private final SongRepository songRepository;

    @Autowired
    public SongService(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    public Song createSong(Song song) {
        return songRepository.save(song);
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id).orElse(null);
    }

    public List<Song> getAllSongs() {
        return songRepository.findAll();
    }

    public Song updateSong(Long id, Song updatedSong) {
        Song existingSong = songRepository.findById(id).orElse(null);
        if (existingSong != null) {
            existingSong.setName(updatedSong.getName());
            existingSong.setAuthor(updatedSong.getAuthor());
            existingSong.setGenre(updatedSong.getGenre());
            return songRepository.save(existingSong);
        }
        return null;
    }

    public void deleteSong(Long id) {
        songRepository.deleteById(id);
    }
}
