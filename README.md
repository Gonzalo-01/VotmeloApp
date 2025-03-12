# VotmeloApp 🎵📱

**VotmeloApp** es una aplicación Android desarrollada en Java que permite a los usuarios crear y gestionar salas de votación de música en tiempo real. La aplicación está integrada con la API de Spotify para buscar canciones y permitir que los usuarios voten por sus favoritas dentro de una sala.

## 🚀 Características principales

- 📌 **Autenticación con Firebase**: Registro e inicio de sesión seguro.
- 🎶 **Integración con Spotify**: Búsqueda de canciones mediante la API de Spotify.
- 🏠 **Creación de salas**: Los usuarios pueden crear salas de votación.
- 📊 **Votación en tiempo real**: Los usuarios pueden votar por las canciones en una sala.
- 🔒 **Gestión de roles**: Administración de usuarios con permisos diferenciados (Administrador, Jugador).
- ☁️ **Base de datos en tiempo real**: Firebase Realtime Database para sincronización de datos.

## 📸 Capturas de pantalla

![image](https://github.com/user-attachments/assets/58391f06-1e0d-4466-9ede-9d35af54dbe6)
![image](https://github.com/user-attachments/assets/801ed347-87df-4045-9733-5144a7492209)
![image](https://github.com/user-attachments/assets/bf079a42-56e7-48ba-9c20-771774668ab5)
![image](https://github.com/user-attachments/assets/c5d9a342-5c10-42ab-96a9-ae2abc11dc67)


## 🛠️ Tecnologías utilizadas

- **Lenguaje:** Java ☕
- **IDE:** Android Studio 🏗️
- **Backend:** Firebase (Authentication & Realtime Database) ☁️
- **APIs:** Spotify API 🎵
- **Control de versiones:** Git & GitHub 🔗

## 📥 Instalación y configuración

### 1️⃣ Clonar el repositorio
```bash
 git clone https://github.com/Gonzalo-01/VotmeloApp.git
```

### 2️⃣ Abrir el proyecto en Android Studio
- Asegúrate de tener **Android Studio** instalado.
- Abre Android Studio y selecciona `Open an Existing Project`.

### 3️⃣ Configurar Firebase
1. Agrega el archivo `google-services.json` en la carpeta `app/`.
2. Habilita **Authentication** y **Realtime Database** en Firebase.

### 4️⃣ Configurar Spotify API
1. Regístrate en [Spotify for Developers](https://developer.spotify.com/dashboard/).
2. Crea una aplicación y obtén las credenciales.
3. Configura los permisos en la app.

### 5️⃣ Ejecutar la aplicación
- Conecta un dispositivo físico o emulador y ejecuta:
```bash
./gradlew build && ./gradlew installDebug
```

## 👨‍💻 Contribuir
¡Las contribuciones son bienvenidas! Sigue estos pasos:
1. Haz un fork del repositorio 🍴
2. Crea una nueva rama (`git checkout -b feature-nueva`)
3. Realiza tus cambios y haz commit (`git commit -m "Agregada nueva funcionalidad"`)
4. Haz push a la rama (`git push origin feature-nueva`)
5. Abre un **Pull Request** 🚀


📧 **Contacto:** Para cualquier duda o sugerencia, contáctame en oscargonzalo403@gmail.com o abre un issue en este repositorio.
