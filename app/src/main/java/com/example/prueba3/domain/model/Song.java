package com.example.prueba3.domain.model;

public class Song {
    private String name;
    private String artist;
    private String uri; // URI de la canción
    private int votes; // Contador de votos

    // Constructor
    public Song(String name, String artist, String uri, int votes) {
        this.name = name;
        this.artist = artist;
        this.uri = uri;
        this.votes = votes;
    }

    // Getters y Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
