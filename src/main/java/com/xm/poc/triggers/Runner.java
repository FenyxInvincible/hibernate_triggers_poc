package com.xm.poc.triggers;

import com.xm.poc.triggers.db.PointsRecords;
import com.xm.poc.triggers.services.PointsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
@Slf4j
public class Runner implements CommandLineRunner {

    private final PointsService pointsService;

    @Override
    public void run(String... args) throws Exception {
        printEntities();

        log.info(" ");
        log.info(" ");
        log.info("Add record");
        pointsService.add(1L, 100);
        printEntities();

        log.info(" ");
        log.info(" ");
        log.info("Add record");
        pointsService.add(1L, 100);
        printEntities();

        try {
            log.info(" ");
            log.info(" ");
            log.info("Add record with exception");
            pointsService.addWithError(1L, 100);
        } catch (Exception e) {
            log.warn("Should be rolled back");
        }
        printEntities();

        log.info(" ");
        log.info(" ");
        log.info("Add record with accidental update");
        pointsService.addWithUpdate(1L, 10);
        printEntities();

        try {
            log.info(" ");
            log.info(" ");
            log.info("Add record with accidental update and exception");
            pointsService.addWithUpdateAndException(1L, 10);
        } catch (Exception e) {
            log.warn("Should be rolled back");
        }
        printEntities();
    }

    private void printEntities() {
        List<PointsRecords> arr = pointsService.pointsRecords();

        for (PointsRecords record: arr) {
            log.info("{}", record);
        }
        log.info("Current points: " + pointsService.getPoints(1L));
    }
}
