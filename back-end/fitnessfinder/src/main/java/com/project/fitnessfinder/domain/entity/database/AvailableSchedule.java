package com.project.fitnessfinder.domain.entity.database;

import java.sql.Time;
import java.time.DayOfWeek;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AvailableSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private DayOfWeek day;

    private Time startTime;

    private Time endTime;

    @ManyToOne
    private VendorOffer vendorOffer;
}
