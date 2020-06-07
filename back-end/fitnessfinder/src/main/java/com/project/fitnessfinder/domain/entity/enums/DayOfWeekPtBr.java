package com.project.fitnessfinder.domain.entity.enums;

public enum DayOfWeekPtBr {
    SEGUNDA("Segunda-Feira"),
    TERCA("Terça-Feira"),
    QUARTA("Quarta-Feira"),
    QUINTA("Quinta-Feira"),
    SEXTA("Sexta-Feira"),
    SABADO("Sabado"),
    DOMINGO("Domingo");

    public final String dayOfWeek;


    DayOfWeekPtBr(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
