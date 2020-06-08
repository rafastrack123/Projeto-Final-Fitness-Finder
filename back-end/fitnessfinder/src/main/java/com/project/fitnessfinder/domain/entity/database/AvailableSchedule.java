package com.project.fitnessfinder.domain.entity.database;

import com.project.fitnessfinder.converter.DayOfWeekIntegerConverter;
import java.sql.Time;
import java.time.DayOfWeek;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class AvailableSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Convert(converter = DayOfWeekIntegerConverter.class)
    private DayOfWeek dayOfWeek;

    private Time startTime;

    private Time endTime;

    @ManyToOne
    private VendorOffer vendorOffer;
}
