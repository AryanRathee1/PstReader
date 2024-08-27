package com.project.pstReader.Repository;

import com.project.pstReader.Model.Entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query("""
            Select t from Token t inner join User u on t.user.serialNo = u.serialNo
            where u.userId = :userId and (t.expired = false or t.revoked = false)
    """)
    List<Token> findAllValidTokenByUser(@Param("userId") Integer userId);

    Optional<Token> findByToken(String token);
}
