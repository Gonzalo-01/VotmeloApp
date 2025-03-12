package com.example.prueba3.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.prueba3.R;
import com.example.prueba3.domain.model.Song;

import java.util.List;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    private List<Song> songList;
    private OnVoteClickListener voteClickListener;

    // Constructor
    public SongAdapter(List<Song> songList, OnVoteClickListener voteClickListener) {
        this.songList = songList;
        this.voteClickListener = voteClickListener;  // Guardamos el listener
    }

    @Override
    public SongViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.song_item, parent, false);
        return new SongViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        Song song = songList.get(position);
        holder.nameTextView.setText(song.getName());
        holder.artistTextView.setText(song.getArtist());
        holder.votesTextView.setText("Votos: " + song.getVotes());

        // Manejar el click en el botón de voto
        holder.voteButton.setOnClickListener(v -> {
            voteClickListener.onVoteClick(song); // Llamamos al método onVoteClick de la actividad
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView artistTextView;
        TextView votesTextView;
        Button voteButton;

        public SongViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.song_name);
            artistTextView = itemView.findViewById(R.id.artist_name);
            votesTextView = itemView.findViewById(R.id.song_votes);
            voteButton = itemView.findViewById(R.id.vote_button);
        }
    }

    // Interfaz para manejar el click en el botón de voto
    public interface OnVoteClickListener {
        void onVoteClick(Song song);
    }
}
