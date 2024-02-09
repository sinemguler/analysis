package com.analysis.analysis.repository;

import com.analysis.analysis.model.WordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WordRepository extends JpaRepository<WordEntity,Long> {
}
