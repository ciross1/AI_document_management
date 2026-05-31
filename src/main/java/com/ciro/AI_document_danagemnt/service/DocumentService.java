package com.ciro.AI_document_danagemnt.service;

import com.ciro.AI_document_danagemnt.dto.AiAnalysisResult;
import com.ciro.AI_document_danagemnt.model.Document;
import com.ciro.AI_document_danagemnt.model.FileType;
import com.ciro.AI_document_danagemnt.model.User;
import com.ciro.AI_document_danagemnt.repository.DocumentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    // --- Abhängigkeiten (nur Interfaces – lose Kopplung) ---
    private final DocumentRepository documentRepository;
    private final UserService userService;                 // zugehörigen User prüfen/laden
    private final FileStorageService fileStorageService;   // Datei speichern
    private final TextExtractionService textExtractionService; // Text auslesen
    private final AiService aiService;                     // klassifizieren

    public DocumentService(DocumentRepository documentRepository,
                           UserService userService,
                           FileStorageService fileStorageService,
                           TextExtractionService textExtractionService,
                           AiService aiService) {
        this.documentRepository = documentRepository;
        this.userService = userService;
        this.fileStorageService = fileStorageService;
        this.textExtractionService = textExtractionService;
        this.aiService = aiService;
    }

    // ============================================================
    //  Kern-Flow: Upload + Verarbeitung (synchron)
    // ============================================================
    public Document upload(MultipartFile file, Long userId) {
        // 1. User muss existieren (wirft sonst EntityNotFoundException)
        User user = userService.findById(userId);

        // 2. Dateityp aus dem Content-Type bestimmen
        FileType fileType = detectFileType(file);

        // 3. Datei physisch speichern → Pfad merken
        String filePath = fileStorageService.store(file);

        // 4. Text aus der Datei extrahieren
        String extractedText = textExtractionService.extract(file, fileType);

        // 5. KI klassifizieren lassen
        AiAnalysisResult analysis = aiService.analyze(extractedText);

        // 6. Entity zusammenbauen
        Document document = new Document();
        document.setFileName(file.getOriginalFilename());
        document.setFileType(fileType);
        document.setFilePath(filePath);
        document.setFileSize(file.getSize());
        document.setUploadedAt(LocalDateTime.now());
        document.setExtractedText(extractedText);
        document.setDocumentType(analysis.documentType());
        document.setSummary(analysis.summary());
        document.setConfidenceScore(analysis.confidenceScore());
        document.setUser(user);

        // 7. Speichern und zurückgeben
        return documentRepository.save(document);
    }

    /**
     * Leitet den FileType aus dem MIME-Type der hochgeladenen Datei ab.
     */
    private FileType detectFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) {
            throw new IllegalArgumentException("Dateityp konnte nicht bestimmt werden");
        }
        if (contentType.equals("application/pdf")) {
            return FileType.PDF;
        }
        if (contentType.startsWith("image/")) {
            return FileType.IMAGE;
        }
        if (contentType.contains("word") || contentType.contains("officedocument.wordprocessingml")) {
            return FileType.WORD;
        }
        throw new IllegalArgumentException("Nicht unterstützter Dateityp: " + contentType);
    }

    // ============================================================
    //  Standard-CRUD
    // ============================================================

    // --- Dokument speichern (neu oder aktualisiert) ---
    public Document save(Document document) {
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
