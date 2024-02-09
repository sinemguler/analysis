package com.analysis.analysis.repository;

import com.analysis.analysis.model.WriterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriterRepository extends JpaRepository<WriterEntity,Long> {
}
