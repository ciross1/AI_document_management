package com.ciro.AI_document_danagemnt.repository;

import com.ciro.AI_document_danagemnt.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, Long> {
}
