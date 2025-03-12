package com.example.prueba3.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prueba3.R;
import com.example.prueba3.domain.model.Room;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;

public class CreateRoomActivity extends AppCompatActivity {

    private EditText roomNameEditText, playerNameEditText;
    private NumberPicker maxParticipantsPicker;
    private Button createRoomButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);

        // Inicializar Firebase
        FirebaseApp.initializeApp(this);

        roomNameEditText = findViewById(R.id.room_name_edit_text);
        playerNameEditText = findViewById(R.id.player_name_edit_text);
        maxParticipantsPicker = findViewById(R.id.max_players_picker);

        // NumberPicker
        maxParticipantsPicker.setMinValue(1); // Valor mínimo
        maxParticipantsPicker.setMaxValue(10); // Valor máximo
        maxParticipantsPicker.setValue(1); // Valor por defecto

        createRoomButton = findViewById(R.id.create_room_button);

        // botón para crear una sala
        createRoomButton.setOnClickListener(v -> createRoom());
    }

    private void createRoom() {
        String roomName = roomNameEditText.getText().toString().trim();
        String playerName = playerNameEditText.getText().toString().trim();
        int maxParticipants = maxParticipantsPicker.getValue();

        // Validar los campos
        if (roomName.isEmpty() || playerName.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        Room newRoom = new Room(roomName, playerName, maxParticipants);

        // Obtener la instancia de Firestore
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Crear la sala en Firestore
        db.collection("rooms")
                .add(newRoom)
                .addOnSuccessListener(documentReference -> {
                    // Sala creada exitosamente
                    String roomId = documentReference.getId();
                    Toast.makeText(CreateRoomActivity.this, "Sala creada exitosamente", Toast.LENGTH_SHORT).show();

                    // Redirigir al usuario a RoomDetailsActivity pasando el roomId
                    Intent intent = new Intent(CreateRoomActivity.this, RoomDetailsActivity.class);
                    intent.putExtra("roomId", roomId);
                    startActivity(intent);
                    finish();
                })
                .addOnFailureListener(e -> {
                    // Error al crear la sala
                    Toast.makeText(CreateRoomActivity.this, "Error al crear la sala", Toast.LENGTH_SHORT).show();
                });
    }
}