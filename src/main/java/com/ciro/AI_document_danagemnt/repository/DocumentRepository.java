package com.ciro.AI_document_danagemnt.repository;

import com.ciro.AI_document_danagemnt.model.Document;
import com.ciro.AI_document_danagemnt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {

    Optional<Document> findByFileName(String fileName);

    List<Document> findByUser(User user);              // alle Dokumente eines Users
}
