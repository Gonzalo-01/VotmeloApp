package com.example.prueba3.domain.model;

public class Room {
    private String roomName;
    private String playerName;
    private int maxParticipants;
    private int currentPlayers;  // Asumiendo que el número de jugadores actuales está en la clase

    // Constructor con parámetros
    public Room(String roomName, String playerName, int maxParticipants) {
        this.roomName = roomName;
        this.playerName = playerName;
        this.maxParticipants = maxParticipants;
        this.currentPlayers = 1;  // Al crear la sala, el primer jugador es el creador
    }

    // Constructor vacío necesario para Firestore (para poder leer y escribir en Firestore)
    public Room() {
        // Firebase necesita un constructor vacío para mapear los datos de Firestore
    }

    // Getters y setters
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public int getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(int currentPlayers) {
        this.currentPlayers = currentPlayers;
    }
}
