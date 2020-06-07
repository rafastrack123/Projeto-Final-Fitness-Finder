package com.project.fitnessfinder.domain.entity.api;

import com.project.fitnessfinder.domain.entity.enums.DayOfWeekPtBr;

public class AvaliableScheduleJson {

    public Long id;

    public DayOfWeekPtBr dayOfWeek;

    public String startingTime;

    public String endingTime;
}
