package com.ciro.AI_document_danagemnt.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Speichert Dateien lokal im Dateisystem.
 *
 * Das Zielverzeichnis kommt aus application.yaml (app.upload-dir),
 * Standard ist "uploads" im Projektverzeichnis.
 */
@Service
public class LocalFileStorageService implements FileStorageService {

    private final Path uploadDir;

    public LocalFileStorageService(@Value("${app.upload-dir:uploads}") String uploadDir) {
        this.uploadDir = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadDir);   // Verzeichnis anlegen, falls nicht vorhanden
        } catch (IOException e) {
            throw new UncheckedIOException("Upload-Verzeichnis konnte nicht erstellt werden: " + this.uploadDir, e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Datei ist leer");
        }

        // Eindeutiger Dateiname, damit sich gleichnamige Uploads nicht überschreiben
        String originalName = file.getOriginalFilename() == null ? "datei" : file.getOriginalFilename();
        String uniqueName = UUID.randomUUID() + "_" + Paths.get(originalName).getFileName();

        Path target = this.uploadDir.resolve(uniqueName);
        try {
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException("Datei konnte nicht gespeichert werden: " + uniqueName, e);
        }

        return target.toString();   // landet in Document.filePath
    }
}
