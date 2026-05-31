package com.ciro.AI_document_danagemnt.service;

import com.ciro.AI_document_danagemnt.model.Document;
import com.ciro.AI_document_danagemnt.model.User;
import com.ciro.AI_document_danagemnt.repository.DocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    // --- Abhängigkeiten ---
    private final DocumentRepository documentRepository;
    private final UserService userService;   // um den zugehörigen User zu prüfen/laden

    public DocumentService(DocumentRepository documentRepository, UserService userService) {
        this.documentRepository = documentRepository;
        this.userService = userService;
    }

    // --- Dokument speichern (neu oder aktualisiert) ---
    public Document save(Document document) {
        // Upload-Zeitpunkt automatisch setzen, falls noch leer
        if (document.getUploadedAt() == null) {
            document.setUploadedAt(LocalDateTime.now());
        }
        return documentRepository.save(document);
    }

    // --- Alle Dokumente holen ---
    public List<Document> findAll() {
        return documentRepository.findAll();
    }

    // --- Ein Dokument per ID holen ---
    public Document findById(Long id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Dokument nicht gefunden mit id: " + id));
    }

    // --- Alle Dokumente eines bestimmten Users holen ---
    public List<Document> findByUser(Long userId) {
        User user = userService.findById(userId);   // wirft Fehler, wenn User nicht existiert
        return documentRepository.findByUser(user);
    }

    // --- Dokument löschen ---
    public void deleteById(Long id) {
        if (!documentRepository.existsById(id)) {
            throw new EntityNotFoundException("Dokument nicht gefunden mit id: " + id);
        }
        documentRepository.deleteById(id);
    }
}
