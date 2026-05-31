package com.ciro.AI_document_danagemnt.model;

import jakarta.persistence.*;

@Entity                          // Diese Klasse = Tabelle in der Datenbank
@Table(name = "users")           // Tabellenname in MySQL
public class User {

    @Id                                                          // Primärschlüssel
    @GeneratedValue(strategy = GenerationType.IDENTITY)          // AUTO_INCREMENT
    private Long id; // long kann nicht null sein. Long kann null sein

    @Column(nullable = false, unique = true)                     // Pflichtfeld, keine Duplikate
    private String email;

    @Column(nullable = false)
    private String password;                                     // wird gehasht gespeichert

    @Enumerated(EnumType.STRING)                                 // speichert "ADMIN" statt 0,1,2
    @Column(nullable = false)
    private Role role;

    // --- Getters & Setters ---

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
}