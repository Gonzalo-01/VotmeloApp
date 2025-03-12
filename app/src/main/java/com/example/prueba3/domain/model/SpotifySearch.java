package com.example.prueba3.domain.model;

import com.spotify.protocol.types.Track;

public class SpotifySearch {
    public static class Result {
        public Tracks tracks;
    }

    public static class Tracks {
        public TrackItem[] items;
    }

    public static class TrackItem {
        public String uri;
        public String name;
        public Artist[] artists;
    }

    public static class Artist {
        public String name;
    }
}
