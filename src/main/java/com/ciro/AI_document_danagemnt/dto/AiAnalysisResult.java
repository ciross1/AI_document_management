package com.ciro.AI_document_danagemnt.dto;

import com.ciro.AI_document_danagemnt.model.DocumentType;

/**
 * Ergebnis der KI-Analyse eines Dokuments.
 *
 * Ein reines DTO (Data Transfer Object) – keine Entity, wird nicht in der DB gespeichert.
 * Es transportiert nur das, was die KI zurückgibt, vom AiService zum DocumentService.
 *
 * record = unveränderliches Datenobjekt (Java 16+); erzeugt automatisch
 * Konstruktor, Getter (documentType(), summary(), ...), equals/hashCode/toString.
 */
public record AiAnalysisResult(
        DocumentType documentType,   // erkannte Kategorie
        String summary,              // Zusammenfassung in einem Satz
        Double confidenceScore       // Sicherheit der Klassifizierung, z.B. 0.95
) {}
