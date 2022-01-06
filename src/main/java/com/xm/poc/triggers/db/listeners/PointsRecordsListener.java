package com.xm.poc.triggers.db.listeners;

import com.xm.poc.triggers.db.PointsRecords;
import com.xm.poc.triggers.db.PointsSummary;
import com.xm.poc.triggers.db.repositories.PointsSummaryRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PointsRecordsListener {

    //lazy to solve circular dependency
    @Lazy
    private final PointsSummaryRepository pointsSummaryRepository;


    @PrePersist
    public void onPrePersist(PointsRecords pointsRecords) {
        PointsSummary summary = pointsSummaryRepository
                .findById(pointsRecords.getUserId())
                .orElse(new PointsSummary(pointsRecords.getUserId()));

        summary.setPointsSummary(summary.getPointsSummary() + pointsRecords.getPoints());
        pointsSummaryRepository.save(summary);
    }

    @PostLoad
    private void saveState(PointsRecords pointsRecords){
        pointsRecords.setSavedState(SerializationUtils.clone(pointsRecords));
    }

    @PostUpdate
    public void onPostUpdate(PointsRecords pointsRecords) {


        //Possible useful case for event listeners
        //throw new IllegalStateException("Updating records for that entity is not allowed");

        //this code doesn't work due to
        //According JPA 2.0 specification
        //In general, the lifecycle method of a portable application should not invoke EntityManager or Query operations, access other entity instances, or modify relationships within the same persistence context.
        /*if(pointsRecords.getSavedState() != null) {
            PointsSummary summary = pointsSummaryRepository
                    .findById(pointsRecords.getUserId())
                    .orElse(new PointsSummary(pointsRecords.getUserId()));

            summary.setPointsSummary(summary.getPointsSummary() + pointsRecords.getPoints() - pointsRecords.getSavedState().getPoints());
            pointsSummaryRepository.save(summary);
        } else {
            throw new IllegalStateException("Old state not found.");
        }*/
    }
}
