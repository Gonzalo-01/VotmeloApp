package com.example.prueba3.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.prueba3.R;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import com.spotify.sdk.android.auth.AuthorizationClient;
import com.spotify.sdk.android.auth.AuthorizationRequest;
import com.spotify.sdk.android.auth.AuthorizationResponse;

public class MainActivity extends AppCompatActivity {

    private static final String CLIENT_ID = "8221b7c40ba2483f82bdee07f0799c9b";
    private static final String REDIRECT_URI = "prueba://callback";
    private static final int REQUEST_CODE = 1337; // Código único para la actividad de autorización
    private static SpotifyAppRemote mSpotifyAppRemote;
    private static String accessToken; // Guardar el token de acceso aquí

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // botón de inicio de sesión
        ImageButton loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(v -> startSpotifyLogin());
    }

    private void startSpotifyLogin() {
        AuthorizationRequest.Builder builder =
                new AuthorizationRequest.Builder(CLIENT_ID, AuthorizationResponse.Type.TOKEN, REDIRECT_URI);

        builder.setScopes(new String[]{"streaming"}); // Permisos requeridos
        AuthorizationRequest request = builder.build();

        AuthorizationClient.openLoginActivity(this, REQUEST_CODE, request);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (requestCode == REQUEST_CODE) {
            AuthorizationResponse response = AuthorizationClient.getResponse(resultCode, intent);

            switch (response.getType()) {
                case TOKEN:
                    // Autorización exitosa, obtener el token de acceso
                    accessToken = response.getAccessToken(); // Guardar el token aquí
                    Log.d("SpotifyAuth", "Token de acceso: " + accessToken);
                    connectToSpotifyAppRemote(); // Conectar a Spotify App Remote
                    break;

                case ERROR:
                    // Manejar errores de autorización
                    Log.e("SpotifyAuth", "Error de autorización: " + response.getError());
                    break;
                default:
                    // Manejar otros casos (cancelación, etc.)
                    Log.d("SpotifyAuth", "Flujo de autorización cancelado o desconocido.");
            }
        }
    }

    private void connectToSpotifyAppRemote() {
        // Configurar los parámetros de conexión
        ConnectionParams connectionParams = new ConnectionParams.Builder(CLIENT_ID)
                .setRedirectUri(REDIRECT_URI)
                .showAuthView(true)
                .build();

        // Conectar al Spotify App Remote
        SpotifyAppRemote.connect(this, connectionParams, new Connector.ConnectionListener() {
            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote;
                Log.d("SpotifyAppRemote", "Conectado a Spotify App Remote");
                // Iniciar RoomActivity después de conectar con Spotify
                Intent intent = new Intent(MainActivity.this, RoomActivity.class);
                startActivity(intent);
                finish(); // Opcional: Para que no vuelva a MainActivity al presionar "Atrás"
            }

            @Override
            public void onFailure(Throwable throwable) {
                Log.e("SpotifyAppRemote", "No se pudo conectar a Spotify App Remote", throwable);
            }
        });
    }

    // Método para obtener el token de acceso de Spotify
    public static String getAccessToken() {
        return accessToken;
    }

    // Método para obtener la referencia de SpotifyAppRemote
    public static SpotifyAppRemote getSpotifyAppRemote() {
        return mSpotifyAppRemote;
    }


}
