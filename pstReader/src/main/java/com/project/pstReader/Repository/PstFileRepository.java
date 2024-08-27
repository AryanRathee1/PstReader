package com.project.pstReader.Repository;

import com.project.pstReader.Model.Entity.PstFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PstFileRepository extends JpaRepository<PstFile, Long> {
}
