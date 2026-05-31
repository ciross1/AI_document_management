package com.ciro.AI_document_danagemnt.service;

import com.ciro.AI_document_danagemnt.model.FileType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Strategy-Auswahl anhand des FileType.
 *
 * NOCH PLATZHALTER: Die echten Extraktoren brauchen externe Libraries,
 * die wir als nächsten Schritt in die pom.xml aufnehmen:
 *   PDF   → org.apache.pdfbox:pdfbox
 *   WORD  → org.apache.poi:poi-ooxml
 *   IMAGE → net.sourceforge.tess4j:tess4j  (Tesseract OCR, native Abhängigkeit)
 */
@Service
public class TextExtractionServiceImpl implements TextExtractionService {

    @Override
    public String extract(MultipartFile file, FileType fileType) {
        return switch (fileType) {
            case PDF   -> extractFromPdf(file);
            case WORD  -> extractFromWord(file);
            case IMAGE -> extractFromImage(file);
        };
    }

    private String extractFromPdf(MultipartFile file) {
        // TODO: mit Apache PDFBox umsetzen (PDDocument.load -> PDFTextStripper)
        return "";
    }

    private String extractFromWord(MultipartFile file) {
        // TODO: mit Apache POI umsetzen (XWPFDocument -> XWPFWordExtractor)
        return "";
    }

    private String extractFromImage(MultipartFile file) {
        // TODO: mit Tesseract OCR (tess4j) umsetzen
        return "";
    }
}
