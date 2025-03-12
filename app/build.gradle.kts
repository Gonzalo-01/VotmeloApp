plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.googleService)
}
android {
    namespace = "com.example.prueba3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.prueba3"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}



dependencies {
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation(libs.firebaseRealTime)
    implementation(libs.firebaseStore)
    implementation ("com.google.code.gson:gson:2.6.1")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(files("libs/spotify-app-remote-release-0.8.0.aar"))
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    implementation ("com.spotify.android:auth:1.2.5") // Maven dependency

    // All other dependencies for your app should also be here:
    implementation ("androidx.browser:browser:1.0.0")
    implementation ("androidx.appcompat:appcompat:1.7.0")

}