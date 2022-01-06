package com.xm.poc.triggers.db;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class PointsSummary {

    public PointsSummary(Long userId) {
        this.userId = userId;
        this.pointsSummary = 0;
    }

    @Id
    private Long userId;

    @Column
    private int pointsSummary;
}
