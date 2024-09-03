package com.project.pstReader.Repository;

import com.project.pstReader.Model.Entity.Email;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends ElasticsearchRepository<Email, Long> {

    @Query("{\"wildcard\": {\"subject\": \"*?0*\"}}")
    Iterable<Email> findBySubject(String subject);

    @Query("{\"wildcard\": {\"senderEmail\": \"*?0*\"}}")
    Iterable<Email> findBySenderEmail(String senderEmail);

    @Query("{\"wildcard\": {\"body\": \"*?0*\"}}")
    Iterable<Email> findByBody(String body);
}
