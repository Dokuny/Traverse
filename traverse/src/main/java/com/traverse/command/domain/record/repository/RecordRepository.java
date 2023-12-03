package com.traverse.command.domain.record.repository;

import com.traverse.command.domain.record.entity.Record;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecordRepository extends JpaRepository<Record, Long> {

	@Query("select distinct r from Record r join fetch r.images where r.id = :recordId")
	Optional<Record> findByIdWithImages(@Param("recordId") Long recordId);
}
