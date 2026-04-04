package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.ResetPasswordRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.ResetPasswordRequestRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.MailService;

@RestController
@RequestMapping("/api/admin")
public class AdminResetApiController {

    @Autowired
    private ResetPasswordRequestRepository resetRepo;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    /* ================= APPROVE ================= */
    @SuppressWarnings("null")
    @Transactional
@PostMapping("/approve/{id}")
public ResponseEntity<?> approve(@PathVariable Long id) {

    ResetPasswordRequest req = resetRepo.findById(id).orElseThrow();

    // ⛔ Block double action
    if (!"PENDING".equals(req.getStatus())) {
        return ResponseEntity.badRequest().build();
    }

    User user = userRepository
            .findByUserEmail(req.getEmail())
            .orElseThrow();

    // ✅ Apply password
    user.setPasswordHash(req.getNewPasswordHash());
    userRepository.save(user);

    // ✅ UPDATE REQUEST (ORDER MATTERS)
    req.setStatus("APPROVED");
    int attempts = req.getAttempts() + 1;
req.setAttempts(attempts);

if (attempts >= 3) {
    req.setLockedUntil(LocalDateTime.now().plusHours(48));
}

    req.setActionTakenAt(LocalDateTime.now());

    resetRepo.save(req); 

    mailService.sendTextMail(
        req.getEmail(),
        "Password Reset Approved",
        "Your password reset request has been approved."
    );

    if (req.getLockedUntil() != null &&
    req.getLockedUntil().isAfter(LocalDateTime.now())) {

    return ResponseEntity.badRequest().body(
        "Password change locked until " + req.getLockedUntil()
    );
}

    return ResponseEntity.ok().build();
}
    @SuppressWarnings("null")
    @Transactional
@PostMapping("/reject/{id}")
public ResponseEntity<?> reject(@PathVariable Long id) {

    ResetPasswordRequest req = resetRepo.findById(id).orElseThrow();

    if (!"PENDING".equals(req.getStatus())) {
        return ResponseEntity.badRequest().build();
    }

    req.setStatus("REJECTED");
    int attempts = req.getAttempts() + 1;
req.setAttempts(attempts);

if (attempts >= 3) {
    req.setLockedUntil(LocalDateTime.now().plusHours(48));
}

req.setActionTakenAt(LocalDateTime.now());
resetRepo.save(req);   
    mailService.sendTextMail(
        req.getEmail(),
        "Password Reset Rejected",
        "Your password reset request has been rejected."
    );
    
if (req.getLockedUntil() != null &&
    req.getLockedUntil().isAfter(LocalDateTime.now())) {

    return ResponseEntity.badRequest().body(
        "Password change locked until " + req.getLockedUntil()
    );
}

    return ResponseEntity.ok().build();
}

}
