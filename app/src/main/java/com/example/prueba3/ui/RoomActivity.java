package com.example.prueba3.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prueba3.R;

public class RoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room); // Asegúrate de tener el layout

        // Botón para crear una nueva sala
        Button createRoomButton = findViewById(R.id.create_room_button);
        createRoomButton.setOnClickListener(v -> createRoom());

        /* Botón para unirse a una sala
        Button joinRoomButton = findViewById(R.id.join_room_button);
        joinRoomButton.setOnClickListener(v -> joinRoom());
         */
    }

    private void createRoom() {
        // Lógica para crear una sala
        Intent intent = new Intent(RoomActivity.this, CreateRoomActivity.class); // Actividad de creación de sala
        startActivity(intent);
    }

    /*
    private void joinRoom() {
        // Lógica para unirse a una sala
        Intent intent = new Intent(RoomActivity.this, JoinRoomActivity.class); // Actividad para unirse a una sala
        startActivity(intent);
    }*/
}