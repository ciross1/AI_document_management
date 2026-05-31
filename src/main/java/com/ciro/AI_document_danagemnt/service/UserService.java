package com.ciro.AI_document_danagemnt.service;

import com.ciro.AI_document_danagemnt.model.User;
import com.ciro.AI_document_danagemnt.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service                              // Spring erkennt diese Klasse als Service-Bean
public class UserService {

    // --- Abhängigkeiten ---
    private final UserRepository userRepository;

    // Verschlüsselt Passwörter (BCrypt), damit nie Klartext in der DB landet
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Constructor Injection: Spring übergibt das Repository automatisch
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // --- Neuen User anlegen ---
    public User createUser(User user) {
        // Doppelte E-Mails verhindern
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("E-Mail wird bereits verwendet: " + user.getEmail());
        }
        // Passwort hashen, bevor es gespeichert wird
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // --- Alle User holen ---
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // --- Einen User per ID holen ---
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User nicht gefunden mit id: " + id));
    }

    // --- Einen User per E-Mail holen (z.B. fürs spätere Login) ---
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User nicht gefunden mit email: " + email));
    }

    // --- User löschen ---
    public void deleteById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User nicht gefunden mit id: " + id);
        }
        userRepository.deleteById(id);
    }
}
