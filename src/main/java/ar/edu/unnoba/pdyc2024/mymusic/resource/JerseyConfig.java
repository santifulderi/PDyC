package ar.edu.unnoba.pdyc2024.mymusic.resource;

import ar.edu.unnoba.pdyc2024.mymusic.resource.PlaylistResource;
import ar.edu.unnoba.pdyc2024.mymusic.resource.SongResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import jakarta.ws.rs.ApplicationPath;

@Configuration
@ApplicationPath("/api")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(PlaylistResource.class);
        register(SongResource.class);

//        preguntar si va aca
//        register(AuthResource.class);
        register(PlaylistSongResource.class);
    }
}
