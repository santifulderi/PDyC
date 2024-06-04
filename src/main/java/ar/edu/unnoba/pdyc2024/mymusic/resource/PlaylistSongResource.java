package ar.edu.unnoba.pdyc2024.mymusic.resource;

import ar.edu.unnoba.pdyc2024.mymusic.dto.SongDTO;
import ar.edu.unnoba.pdyc2024.mymusic.model.Song;
import ar.edu.unnoba.pdyc2024.mymusic.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists/{id}/songs")
public class PlaylistSongResource {

    private final PlaylistService playlistService;

    public PlaylistSongResource(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Void> addSongToPlaylist(@PathVariable Long id, @RequestBody Long songId) {
        playlistService.addSongToPlaylist(id, songId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long id, @PathVariable Long songId) {
        playlistService.removeSongFromPlaylist(id, songId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<SongDTO> getAllSongsInPlaylist(@PathVariable Long id) {
        return playlistService.getAllSongsInPlaylist(id)
                .stream()
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
