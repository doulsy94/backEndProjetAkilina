package com.sy.backEndApiAkilina.configuration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class AudioConfig {
    private static final List<String> AUDIO_EXTENSIONS = Arrays.asList(".mp3", ".wav", ".ogg", ".flac", ".m4a");

    public static void saveAudio(String uploadDir, File file) {
        // Parcourez les fichiers dans le répertoire source
        for (String extension : AUDIO_EXTENSIONS) {
            if (file.getName().endsWith(extension)) {
                // Construisez les chemins complets pour les fichiers source et de destination
                File destinationFile = new File(uploadDir, file.getName());
                try {
                    // Copiez le fichier audio
                    Files.copy(file.toPath(), destinationFile.toPath());
                    System.out.println("Copié " + file.getName());
                } catch (IOException e) {
                    System.out.println("Erreur lors de la copie " + file.getName());
                }
                break;
            }
        }
    }

}

