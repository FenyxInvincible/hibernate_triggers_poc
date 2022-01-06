package com.xm.poc.triggers.db.repositories;

import com.xm.poc.triggers.db.PointsRecords;
import com.xm.poc.triggers.db.PointsSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PointsSummaryRepository extends JpaRepository<PointsSummary, Long> {
}
