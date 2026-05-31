package com.ciro.AI_document_danagemnt.service;

import com.ciro.AI_document_danagemnt.dto.AiAnalysisResult;
import com.ciro.AI_document_danagemnt.model.DocumentType;
import org.springframework.stereotype.Service;

/**
 * KI-Analyse über OpenAI (GPT-4o).
 *
 * NOCH PLATZHALTER: Hier kommt als nächster Schritt der echte API-Aufruf hin.
 * Geplanter Ablauf:
 *   1. Prompt bauen: "Klassifiziere folgenden Text in eine dieser Kategorien:
 *      ARBEITSVERZEICHNIS, SPRINGBOOT_PROJEKTE, PROJEKTIDEEN, PERSONALVERZEICHNIS.
 *      Gib JSON zurück: { documentType, summary, confidenceScore }." + text
 *   2. Aufruf via Spring RestClient an https://api.openai.com/v1/chat/completions
 *      (API-Key aus application.yaml / Umgebungsvariable).
 *   3. JSON-Antwort mit Jackson in AiAnalysisResult mappen.
 *
 * Bis dahin gibt diese Implementierung ein neutrales Ergebnis zurück, damit die
 * synchrone Pipeline (Upload -> Extraktion -> KI -> Speichern) durchläuft.
 */
@Service
public class OpenAiService implements AiService {

    @Override
    public AiAnalysisResult analyze(String text) {
        // TODO: echten OpenAI-Aufruf einbauen (RestClient + Prompt + JSON-Parsing)
        return new AiAnalysisResult(
                DocumentType.PROJEKTIDEEN,
                "Noch keine KI-Analyse – Platzhalter",
                0.0
        );
    }
}
