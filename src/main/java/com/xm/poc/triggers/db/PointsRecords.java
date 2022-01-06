package com.xm.poc.triggers.db;

import com.xm.poc.triggers.db.listeners.PointsRecordsListener;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.SerializationUtils;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@EntityListeners(PointsRecordsListener.class)
@ToString
public class PointsRecords implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private Long userId;

    @Column
    private int points;

    @Transient
    private PointsRecords savedState;
}
