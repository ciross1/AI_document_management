package com.ciro.AI_document_danagemnt.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Speichert hochgeladene Dateien physisch ab (lokal, später z.B. S3).
 *
 * Hinter ein Interface gelegt, damit der Speicherort austauschbar ist,
 * ohne dass DocumentService geändert werden muss.
 */
public interface FileStorageService {

    /**
     * Speichert die Datei und gibt den Pfad/Schlüssel zurück, unter dem sie liegt.
     *
     * @param file die hochgeladene Datei
     * @return Pfad zur gespeicherten Datei (kommt in Document.filePath)
     */
    String store(MultipartFile file);
}
