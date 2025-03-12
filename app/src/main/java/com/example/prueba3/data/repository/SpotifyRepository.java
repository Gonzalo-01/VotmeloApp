package com.example.prueba3.data.repository;
import android.util.Log;

import com.example.prueba3.data.remote.SpotifyApi;
import com.example.prueba3.domain.model.Song;
import com.example.prueba3.domain.model.SearchResponse;
import com.google.firebase.firestore.FirebaseFirestore;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpotifyRepository {
    private static final String BASE_URL = "https://api.spotify.com/";
    private FirebaseFirestore db;
    private SpotifyApi spotifyApi;

    public SpotifyRepository() {
        db = FirebaseFirestore.getInstance();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        spotifyApi = retrofit.create(SpotifyApi.class);
    }

    // Buscar canción en la API de Spotify
    public void searchSong(String songName, String accessToken, Callback<SearchResponse> callback) {
        String authorization = "Bearer " + accessToken;
        Call<SearchResponse> call = spotifyApi.searchSong(songName, "track", 1, authorization);
        call.enqueue(callback);
    }

    // Guardar canción en Firestore
    public void saveSongToFirestore(String roomId, Song song) {
        db.collection("rooms")
                .document(roomId)
                .collection("songs")
                .document(song.getUri())
                .set(song)
                .addOnSuccessListener(aVoid -> Log.d("SpotifyRepository", "Canción agregada a Firestore"))
                .addOnFailureListener(e -> Log.e("SpotifyRepository", "Error al guardar la canción en Firestore", e));
    }

    // Actualizar votos en Firestore
    public void updateVotesInFirestore(String roomId, Song song) {
        db.collection("rooms")
                .document(roomId)
                .collection("songs")
                .document(song.getUri())
                .update("votes", song.getVotes())
                .addOnSuccessListener(aVoid -> Log.d("SpotifyRepository", "Voto actualizado en Firestore"))
                .addOnFailureListener(e -> Log.e("SpotifyRepository", "Error al actualizar los votos", e));
    }

    // Reproducir canción en Spotify
    public void playSongOnSpotify(SpotifyAppRemote mSpotifyAppRemote, Song song) {
        if (mSpotifyAppRemote != null && song != null) {
            mSpotifyAppRemote.getPlayerApi().play(song.getUri());
            Log.d("SpotifyRepository", "Reproduciendo canción: " + song.getName());
        }
    }
}
