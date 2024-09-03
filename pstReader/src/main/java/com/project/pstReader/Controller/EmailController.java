package com.project.pstReader.Controller;

import com.project.pstReader.Model.Entity.Email;
import com.project.pstReader.Service.EmailSearchService;
import com.project.pstReader.Service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;
    private final EmailSearchService emailSearchService;

    @GetMapping("/allMails")
    public ResponseEntity<Iterable<Email>> getEmails() {
        try {
            Iterable<Email> emails = emailService.getAllEmails();
            return ResponseEntity.ok(emails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/subject")
    public ResponseEntity<Iterable<Email>> searchEmailsBySubject(@RequestParam String subject) {
        try {
            Iterable<Email> emails = emailSearchService.searchEmailsBySubject(subject);
            return ResponseEntity.ok(emails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/sender")
    public ResponseEntity<Iterable<Email>> searchEmailsBySenderEmail(@RequestParam String senderEmail) {
        try {
            Iterable<Email> emails = emailSearchService.searchEmailsBySenderEmail(senderEmail);
            return ResponseEntity.ok(emails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/body")
    public ResponseEntity<Iterable<Email>> searchEmailsByBody(@RequestParam String body) {
        try {
            Iterable<Email> emails = emailSearchService.searchEmailsByBody(body);
            return ResponseEntity.ok(emails);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }
}
