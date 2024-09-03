package com.project.pstReader.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName="emails")
public class Email {

    @Id
    private String email_id; // Change to String
    private String subject;
    private String senderEmail;
    private String body;
    private String recipient;
}
