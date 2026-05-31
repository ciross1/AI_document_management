package com.ciro.AI_document_danagemnt.service;

import com.ciro.AI_document_danagemnt.dto.AiAnalysisResult;

/**
 * Abstraktion über die KI-Analyse.
 *
 * Warum ein Interface? Der DocumentService kennt nur diese Methode, nicht die
 * konkrete Technik (OpenAI, ein anderes Modell, oder ein Mock im Test).
 * → In Unit-Tests kann AiService mit Mockito gemockt werden, ganz ohne echte API.
 */
public interface AiService {

    /**
     * Analysiert den extrahierten Text eines Dokuments und klassifiziert ihn.
     *
     * @param text der rohe Text aus dem Dokument
     * @return Kategorie, Zusammenfassung und Konfidenzwert
     */
    AiAnalysisResult analyze(String text);
}
