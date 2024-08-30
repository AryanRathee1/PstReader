package com.project.pstReader.Repository;

import com.project.pstReader.Model.Entity.PstAccessTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PstAccessTableRepository extends JpaRepository<PstAccessTable, Integer> {
    Optional<PstAccessTable> findByUserIdAndPstId(Integer userId, Integer pstId);
}
