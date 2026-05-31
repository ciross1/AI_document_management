package com.ciro.AI_document_danagemnt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "documents")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Datei-Informationen ---

    @Column(nullable = false)
    private String fileName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FileType fileType;           // PDF, WORD, IMAGE

    @Column(nullable = false)
    private String filePath;

    @Column(nullable = false)
    private Long fileSize;               // Größe in Bytes

    @Column(nullable = false)
    private LocalDateTime uploadedAt;    // modernes Java statt Date

    // --- KI-Ergebnisse ---

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;   // deine persönlichen Verzeichnisse

    @Column(length = 500)
    private String summary;              // KI-Zusammenfassung

    private Double confidenceScore;      // z.B. 0.95 = 95% sicher

    @Column(columnDefinition = "TEXT")
    private String extractedText;        // roher Text aus dem Dokument

    // --- Beziehung zu User ---

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)  // Fremdschlüssel in der Tabelle
    private User user;

    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public FileType getFileType() { return fileType; }
    public void setFileType(FileType fileType) { this.fileType = fileType; }

    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }

    public Long getFileSize() { return fileSize; }
    public void setFileSize(Long fileSize) { this.fileSize = fileSize; }

    public LocalDateTime getUploadedAt() { return uploadedAt; }
    public void setUploadedAt(LocalDateTime uploadedAt) { this.uploadedAt = uploadedAt; }

    public DocumentType getDocumentType() { return documentType; }
    public void setDocumentType(DocumentType documentType) { this.documentType = documentType; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }

    public String getExtractedText() { return extractedText; }
    public void setExtractedText(String extractedText) { this.extractedText = extractedText; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}