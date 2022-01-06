package com.xm.poc.triggers.services;

import com.xm.poc.triggers.db.PointsRecords;
import com.xm.poc.triggers.db.PointsSummary;
import com.xm.poc.triggers.db.repositories.PointsRecordsRepository;
import com.xm.poc.triggers.db.repositories.PointsSummaryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointsService {

    private final PointsRecordsRepository pointsRecordsRepository;
    private final PointsSummaryRepository pointsSummaryRepository;

    public List<PointsRecords> pointsRecords() {
        return pointsRecordsRepository.findAll();
    }

    @Transactional
    public void add(Long userId, int points) {
        PointsRecords record = new PointsRecords();
        record.setPoints(points);
        record.setUserId(userId);
        pointsRecordsRepository.save(record);
    }

    @Transactional
    public void addWithError(Long userId, int points) {
        PointsRecords record = new PointsRecords();
        record.setPoints(points);
        record.setUserId(userId);
        pointsRecordsRepository.saveAndFlush(record);

        log.info("Not rolled back value {}", pointsSummaryRepository.findById(userId));

        throw new IllegalStateException();
    }

    @Transactional
    //Implementation for accidental update
    public void addWithUpdate(Long userId, int points) {
        PointsRecords record = new PointsRecords();
        record.setId(1L);//existing row
        record.setPoints(points);
        record.setUserId(userId);
        record = pointsRecordsRepository.saveAndFlush(record);

        PointsSummary e = pointsSummaryRepository.findById(userId).orElse(new PointsSummary(userId));
        e.setPointsSummary(e.getPointsSummary() - record.getSavedState().getPoints() + record.getPoints());
    }

    @Transactional
    //Implementation for accidental update
    public void addWithUpdateAndException(Long userId, int points) {
        PointsRecords record = new PointsRecords();
        record.setId(2L);//existing row
        record.setPoints(points);
        record.setUserId(userId);
        record = pointsRecordsRepository.saveAndFlush(record);

        PointsSummary e = pointsSummaryRepository.findById(userId).orElse(new PointsSummary(userId));
        e.setPointsSummary(e.getPointsSummary() - record.getSavedState().getPoints() + record.getPoints());

        log.info("Not rolled back value {}", pointsSummaryRepository.findById(userId));

        throw new IllegalStateException("Somewhere something wrong");
    }

    public int getPoints(long userId) {
        return pointsSummaryRepository.
                findById(userId).orElse(new PointsSummary(userId)).getPointsSummary();
    }
}
