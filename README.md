# AI_document_management
A comprehensive backend platform for the automated processing, classification, and management of documents using AI.


# AI Document Management

A Spring Boot backend for the automated management of documents. Files are uploaded,
their text is extracted automatically, and then classified and summarized by an AI.
This allows documents to be assigned to the correct categories without any manual effort.

---

## Features

- **Document Upload** – Supported formats: PDF, Word (.docx), Images (JPG, PNG, etc.)
- **Text Extraction** – PDF via Apache PDFBox, Word via Apache POI, Images via Tesseract OCR *(in progress)*
- **AI Analysis** – Classification and summarization of extracted text via the OpenAI API *(in progress)*
- **Automatic Categorization** – Documents are assigned to one of the following categories:
    - `ARBEITSVERZEICHNIS`
    - `SPRINGBOOT_PROJEKTE`
    - `PROJEKTIDEEN`
    - `PERSONALVERZEICHNIS`
- **User Management** – Registration, login, and role assignment (ADMIN, MANAGER, USER)
- **Security** – Passwords are hashed with BCrypt; access control via Spring Security
- **Local File Storage** – Uploaded files are stored on the server (path is configurable)

---

## Tech Stack

| Area | Technology |
|---|---|
| Framework | Spring Boot 4.0.6 |
| Language | Java 21 |
| Database | MySQL 8 |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security + BCrypt |
| Build | Maven Wrapper (mvnw) |
| Infrastructure | Docker / Docker Compose |

---

## Project Structure

```
src/main/java/com/ciro/AI_document_management/
├── model/
│   ├── Document.java          # Document entity (JPA)
│   ├── User.java              # User entity (JPA)
│   ├── DocumentType.java      # Enum: categories
│   ├── FileType.java          # Enum: PDF, WORD, IMAGE
│   └── Role.java              # Enum: ADMIN, MANAGER, USER
├── dto/
│   └── AiAnalysisResult.java  # DTO for AI result (Java Record)
├── repository/
│   ├── DocumentRepository.java
│   └── UserRepository.java
└── service/
    ├── DocumentService.java           # Core logic: upload pipeline
    ├── UserService.java               # User management
    ├── AiService.java                 # Interface: AI analysis
    ├── OpenAiService.java             # Implementation: OpenAI GPT
    ├── FileStorageService.java        # Interface: file storage
    ├── LocalFileStorageService.java   # Implementation: local file system
    ├── TextExtractionService.java     # Interface: text extraction
    └── TextExtractionServiceImpl.java # Implementation: PDFBox, POI, Tesseract
```

---

## Upload Pipeline

When a document is uploaded, the application executes the following steps:

1. User is loaded and validated by ID
2. File type is determined from the MIME type
3. File is physically stored on the local file system
4. Text is extracted from the file
5. AI analyzes the text and returns a category, summary, and confidence score
6. The completed `Document` object is saved to the database

---

## Prerequisites

- Java 21
- Docker and Docker Compose
- Maven (or use the included wrapper `./mvnw`)

---

## Quick Start

**1. Start the database**

```bash
docker-compose up -d
```

**2. Create environment variables**

Create a `daten.env` file in the project root:

```properties
DB_USERNAME=root
DB_PASSWORD=root
```

**3. Start the application**

```bash
./mvnw spring-boot:run
```

The application is available at `http://localhost:8080`.

---

## Configuration

| Parameter | Default | Description |
|---|---|---|
| `spring.datasource.url` | `jdbc:mysql://localhost:3306/ai_document_db` | Database connection |
| `app.upload-dir` | `uploads` | Target directory for uploaded files |
| `DB_USERNAME` | – | MySQL username (environment variable) |
| `DB_PASSWORD` | – | MySQL password (environment variable) |

---

## Architecture Decisions

- **Interfaces over concrete classes** – `AiService`, `FileStorageService`, and
  `TextExtractionService` are interfaces. Implementations can be swapped out
  (e.g. local storage to S3, OpenAI to another model) without touching `DocumentService`.
  In tests, all three can be mocked with Mockito.

- **Java Records as DTOs** – `AiAnalysisResult` is an immutable record object.
  It only carries the AI result and is never persisted to the database.

- **BCrypt Hashing** – Passwords are never stored in plain text.

---

## Open Items (in progress)

- Real OpenAI API integration (RestClient + JSON mapping)
- Text extraction with Apache PDFBox, Apache POI, and Tesseract OCR
- REST controllers for all endpoints
- Spring Security configuration (JWT or session-based)
- Unit tests for the service layer

---

## License

Apache License 2.0 – see [LICENSE](LICENSE)