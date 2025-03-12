package com.example.prueba3.domain.model;

import java.util.List;

public class SearchResponse {
    public Tracks tracks;  // Esta clase contiene los resultados de la búsqueda de canciones

    public static class Tracks {
        public List<TrackItem> items;  // Lista de las canciones encontradas
    }

    public static class TrackItem {
        public String uri;    // El URI de la canción
        public String name;   // Nombre de la canción
        public Artist[] artists;  // Lista de artistas de la canción
    }

    public static class Artist {
        public String name;  // Nombre del artista
    }
}
