package com.project.fitnessfinder.domain.entity.api;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.fitnessfinder.domain.entity.enums.DayOfWeekPtBr;
import lombok.Builder;

@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public class AvailableScheduleJson {

    public Long id;

    public DayOfWeekPtBr dayOfWeek;

    public String startTime;

    public String endTime;
}
