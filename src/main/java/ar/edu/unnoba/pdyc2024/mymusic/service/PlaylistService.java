package ar.edu.unnoba.pdyc2024.mymusic.service;

import ar.edu.unnoba.pdyc2024.mymusic.model.Playlist;
import ar.edu.unnoba.pdyc2024.mymusic.model.Song;
import ar.edu.unnoba.pdyc2024.mymusic.model.User;
import ar.edu.unnoba.pdyc2024.mymusic.repository.PlaylistRepository;
import ar.edu.unnoba.pdyc2024.mymusic.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongService songService;

    @Transactional
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Playlist createPlaylist(String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findFirstByEmail(authentication.getName());

        Playlist playlist = new Playlist();
        playlist.setName(name);
        playlist.setOwner(user);

        return playlistRepository.save(playlist);
    }

    @Transactional
    public void addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist not found"));
        validatePlaylistOwner(playlist);

        Song song = songService.getSongById(songId);

        if (validateSongPresence(playlist, song))
            throw new RuntimeException("The song is already in the playlist");

        playlist.getSongs().add(song);

        playlistRepository.save(playlist);
    }

    @Transactional
    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist not found"));
        validatePlaylistOwner(playlist);

        playlist.getSongs().removeIf(song -> song.getId().equals(songId));
        playlistRepository.save(playlist);
    }

    @Transactional
    public void updatePlaylistName(Long playlistId, String newName) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist not found"));
        validatePlaylistOwner(playlist);

        playlist.setName(newName);
        playlistRepository.save(playlist);
    }

    public List<Song> getAllSongsInPlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist not found"));
        return playlist.getSongs();
    }

    @Transactional
    public void deletePlaylist(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("Playlist not found"));
        validatePlaylistOwner(playlist);

        playlistRepository.deleteById(playlistId);
    }

    @Transactional
//    @Override
    public List<Playlist> getPlaylistsByUser(String mail) {
        User user = userRepository.findFirstByEmail(mail);
        if (user != null) {
            return playlistRepository.findByOwner_Id(user.getId());
        }
        return null;
    }

    private void validatePlaylistOwner(Playlist playlist) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!playlist.getOwner().getUsername().equals(authentication.getName())) {
            throw new RuntimeException("User is not the owner of this playlist");
        }
    }


    private boolean validateSongPresence(Playlist playlist, Song song) {
        return playlist.getSongs().contains(song);
    }
}
