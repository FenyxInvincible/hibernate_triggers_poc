package com.xm.poc.triggers.db.repositories;

import com.xm.poc.triggers.db.PointsRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointsRecordsRepository extends JpaRepository<PointsRecords, Long> {
}
