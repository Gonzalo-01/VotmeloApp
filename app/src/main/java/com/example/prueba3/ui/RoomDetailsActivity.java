package com.example.prueba3.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prueba3.R;
import com.example.prueba3.domain.model.Room;
import com.example.prueba3.domain.model.SearchResponse;
import com.example.prueba3.domain.model.Song;
import com.example.prueba3.data.repository.SpotifyRepository;
import com.google.firebase.firestore.FirebaseFirestore;

import com.spotify.android.appremote.api.SpotifyAppRemote;

import java.util.ArrayList;
import java.util.List;

import retrofit2.*;

public class RoomDetailsActivity extends AppCompatActivity implements SongAdapter.OnVoteClickListener{

    private TextView currentSongTextView;
    private boolean isPlaying = false;
    private ImageButton pauseButton;
    private TextView roomNameTextView, playerNameTextView, currentPlayersTextView;
    private RecyclerView songsRecyclerView;
    private EditText songSearchEditText;
    private Button addSongButton;
    private SongAdapter songAdapter;
    private List<Song> songList;
    private FirebaseFirestore db;
    private String roomId;

    private SpotifyAppRemote mSpotifyAppRemote;
    private SpotifyRepository spotifyRepository; // Instancia del repositorio

    @Override
    public void onVoteClick(Song song) {
        song.setVotes(song.getVotes() + 1);
        spotifyRepository.updateVotesInFirestore(roomId, song);
        songAdapter.notifyDataSetChanged();
        playMostVotedSong();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);

        roomId = getIntent().getStringExtra("roomId");
        db = FirebaseFirestore.getInstance();
        spotifyRepository = new SpotifyRepository(); // Inicializar el repositorio

        currentSongTextView = findViewById(R.id.current_song_text_view);
        roomNameTextView = findViewById(R.id.room_name_text_view);
        playerNameTextView = findViewById(R.id.player_name_text_view);
        currentPlayersTextView = findViewById(R.id.current_players_text_view);
        songsRecyclerView = findViewById(R.id.songs_recycler_view);
        songSearchEditText = findViewById(R.id.song_search_edit_text);
        addSongButton = findViewById(R.id.add_song_button);

        songList = new ArrayList<>();
        songAdapter = new SongAdapter(songList, this);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        songsRecyclerView.setAdapter(songAdapter);

        pauseButton = findViewById(R.id.pause_button);
        pauseButton.setOnClickListener(v -> togglePlayPause());

        mSpotifyAppRemote = MainActivity.getSpotifyAppRemote();
        if (mSpotifyAppRemote == null) {
            Log.e("RoomDetailsActivity", "Spotify no está conectado.");
        }

        loadRoomDetails();
        addSongButton.setOnClickListener(v -> {
            addSongToPlaylist();
            playMostVotedSong();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSpotifyAppRemote != null) {
            mSpotifyAppRemote.getPlayerApi().pause();
            SpotifyAppRemote.disconnect(mSpotifyAppRemote);
            Log.d("RoomDetailsActivity", "Conexión con Spotify desconectada.");
        }
    }

    private void loadRoomDetails() {
        db.collection("rooms").document(roomId).get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        Room room = documentSnapshot.toObject(Room.class);
                        if (room != null) {
                            roomNameTextView.setText(room.getRoomName());
                            playerNameTextView.setText(room.getPlayerName());
                            currentPlayersTextView.setText("Jugadores actuales: " + room.getCurrentPlayers());
                        }
                    }
                })
                .addOnFailureListener(e -> Log.e("RoomDetailsActivity", "Error al cargar los detalles de la sala", e));
    }

    private void addSongToPlaylist() {
        String songQuery = songSearchEditText.getText().toString();
        if (songQuery.isEmpty()) {
            Toast.makeText(this, "Por favor, ingresa una canción.", Toast.LENGTH_SHORT).show();
            return;
        }

        spotifyRepository.searchSong(songQuery, MainActivity.getAccessToken(), new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                if (response.isSuccessful()) {
                    SearchResponse searchResponse = response.body();
                    if (searchResponse != null && searchResponse.tracks != null) {
                        List<SearchResponse.TrackItem> trackItems = searchResponse.tracks.items;

                        if (!trackItems.isEmpty()) {
                            SearchResponse.TrackItem trackItem = trackItems.get(0);
                            String songName = trackItem.name;
                            StringBuilder artists = new StringBuilder();
                            for (SearchResponse.Artist artist : trackItem.artists) {
                                artists.append(artist.name).append(", ");
                            }
                            if (artists.length() > 0) {
                                artists.setLength(artists.length() - 2);
                            }
                            String uri = trackItem.uri;
                            Song song = new Song(songName, artists.toString(), uri, 0);
                            songList.add(song);
                            songAdapter.notifyDataSetChanged();
                            spotifyRepository.saveSongToFirestore(roomId, song);
                            playMostVotedSong();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                Log.e("RoomDetailsActivity", "Error al realizar la búsqueda", t);
                Toast.makeText(RoomDetailsActivity.this, "Error en la búsqueda de canciones", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void playMostVotedSong() {
        Song mostVotedSong = getMostVotedSong();
        if (mostVotedSong != null) {
            spotifyRepository.playSongOnSpotify(mSpotifyAppRemote, mostVotedSong);
            currentSongTextView.setText("Canción actual: " + mostVotedSong.getName());
            isPlaying = true;
            pauseButton.setImageResource(R.drawable.play_pause);
        }
    }

    private Song getMostVotedSong() {
        Song mostVotedSong = null;
        int maxVotes = -1;
        for (Song song : songList) {
            if (song.getVotes() > maxVotes) {
                mostVotedSong = song;
                maxVotes = song.getVotes();
            }
        }
        return mostVotedSong;
    }

    private void togglePlayPause() {
        if (isPlaying) {
            mSpotifyAppRemote.getPlayerApi().pause();
            pauseButton.setImageResource(R.drawable.play_pause);
            isPlaying = false;
        } else {
            mSpotifyAppRemote.getPlayerApi().resume();
            pauseButton.setImageResource(R.drawable.play_pause);
            isPlaying = true;
        }
    }
}