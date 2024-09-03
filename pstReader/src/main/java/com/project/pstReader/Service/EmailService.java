package com.project.pstReader.Service;

import com.pff.PSTMessage;
import com.project.pstReader.Model.Entity.Email;
import com.project.pstReader.Repository.EmailRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    private final EmailRepository emailRepository;
    private final HtmlToJsonConverter htmlToJsonConverter;

    public void saveEmail(PSTMessage email) {
        try {
            Email emailEntity = new Email();
            emailEntity.setSubject(email.getSubject());
            emailEntity.setSenderEmail(email.getSenderEmailAddress());
            String jsonBody = htmlToJsonConverter.convertHtmlToJson(email.getBodyHTML());
            emailEntity.setBody(jsonBody);
            emailEntity.setRecipient(email.getDisplayTo());

            emailRepository.save(emailEntity);
        } catch (IOException e) {
            logger.error("Error converting HTML to JSON", e);
        }
    }

    public Iterable<Email> getAllEmails() {
        return emailRepository.findAll();
    }
}
