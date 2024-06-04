package ar.edu.unnoba.pdyc2024.mymusic.dto;

import ar.edu.unnoba.pdyc2024.mymusic.model.Genre;

public class SongDTO {

    private Long id;
    private String name;
    private String author;
    private Genre genre;

    public SongDTO() {
    }

    public SongDTO(Long id, String name, String author, Genre genre) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }
}
