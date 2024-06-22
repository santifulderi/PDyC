package ar.edu.unnoba.pdyc2024.mymusic.resource;

import ar.edu.unnoba.pdyc2024.mymusic.dto.PlaylistDTO;
import ar.edu.unnoba.pdyc2024.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc2024.mymusic.service.PlaylistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
public class PlaylistResource {

    private final PlaylistService playlistService;

    @Autowired
    public PlaylistResource(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping
    public List<PlaylistDTO> getAllPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        return playlists.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<PlaylistDTO> createPlaylist(@RequestBody PlaylistDTO playlistDTO) {
        Playlist playlist = playlistService.createPlaylist(playlistDTO.getName());
        PlaylistDTO createdPlaylistDTO = convertToDTO(playlist);
        return new ResponseEntity<>(createdPlaylistDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePlaylistName(@PathVariable Long id, @RequestBody PlaylistDTO playlistDTO) {
        playlistService.updatePlaylistName(id, playlistDTO.getName());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
//        return ResponseEntity.noContent().build();
        return ResponseEntity.ok("Playlist removed correctly.");
    }

    @GetMapping("/my-playlists")
    public List<PlaylistDTO> getMyPlaylists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //obtener el usuario autenticado
        String mail = authentication.getName(); //obtener el nombre del usuario autenticado (mail)
        List<Playlist> playlists = playlistService.getPlaylistsByUser(mail);

        return playlists.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    //Mapeo de una playlist a un PlaylistDTO
    private PlaylistDTO convertToDTO(Playlist playlist) {
        PlaylistDTO dto = new PlaylistDTO();
        dto.setId(playlist.getId());
        dto.setName(playlist.getName());
        if (playlist.getSongs() != null) {
            dto.setSongCount(playlist.getSongs().size());
        } else {
            dto.setSongCount(0);
        }
        return dto;
    }
}
