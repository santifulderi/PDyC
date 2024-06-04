package ar.edu.unnoba.pdyc2024.mymusic.resource;

import ar.edu.unnoba.pdyc2024.mymusic.dto.SongDTO;
import ar.edu.unnoba.pdyc2024.mymusic.model.Song;
import ar.edu.unnoba.pdyc2024.mymusic.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongResource {

    private final SongService songService;

    @Autowired
    public SongResource(SongService songService) {
        this.songService = songService;
    }

    @PostMapping
    public Song createSong(@RequestBody Song song) {
        return songService.createSong(song);
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        return songService.getSongById(id);
    }

    @PutMapping("/{id}")
    public Song updateSong(@PathVariable Long id, @RequestBody Song song) {
        return songService.updateSong(id, song);
    }

    @DeleteMapping("/{id}")
    public void deleteSong(@PathVariable Long id) {
        songService.deleteSong(id);
    }

    @GetMapping
    public List<SongDTO> getAllSongs() {
        List<Song> songs = songService.getAllSongs();
        return songs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private SongDTO convertToDTO(Song song) {
        SongDTO dto = new SongDTO();
        dto.setId(song.getId());
        dto.setName(song.getName());
        dto.setAuthor(song.getAuthor());
        dto.setGenre(song.getGenre());
        return dto;
    }
}
