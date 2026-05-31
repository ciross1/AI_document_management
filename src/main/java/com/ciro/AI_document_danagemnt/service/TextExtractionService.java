package com.ciro.AI_document_danagemnt.service;

import com.ciro.AI_document_danagemnt.model.FileType;
import org.springframework.web.multipart.MultipartFile;

/**
 * Holt den reinen Text aus einer Datei.
 *
 * Je nach FileType wird intern eine andere Technik genutzt (Strategy-Pattern):
 *   PDF   → Apache PDFBox
 *   WORD  → Apache POI
 *   IMAGE → Tesseract OCR
 */
public interface TextExtractionService {

    /**
     * Extrahiert den Text aus der Datei.
     *
     * @param file     die hochgeladene Datei
     * @param fileType der erkannte Dateityp (bestimmt die Extraktionsmethode)
     * @return der extrahierte Text
     */
    String extract(MultipartFile file, FileType fileType);
}
