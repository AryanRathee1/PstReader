package com.project.pstReader.Service;

import com.project.pstReader.Model.Entity.Email;
import com.project.pstReader.Repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSearchService {

    private final EmailRepository emailRepository;

    public Iterable<Email> searchEmailsBySubject(String subject) {
        // Wildcard search by subject
        return emailRepository.findBySubject(subject);
    }

    public Iterable<Email> searchEmailsBySenderEmail(String senderEmail) {
        // Wildcard search by senderName
        return emailRepository.findBySenderEmail(senderEmail);
    }

    public Iterable<Email> searchEmailsByBody(String body) {
        // Wildcard search by body
        return emailRepository.findByBody(body);
    }

}
