package com.project.pstReader.Repository;

import com.project.pstReader.Model.Entity.PstFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PstFileRepository extends JpaRepository<PstFile, Long> {
    Optional<PstFile> findByPstId(Integer pstId);
}
