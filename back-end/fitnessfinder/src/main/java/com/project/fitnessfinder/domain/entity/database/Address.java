package com.project.fitnessfinder.domain.entity.database;

import javax.persistence.Embeddable;
import lombok.Data;

@Embeddable
@Data
public class Address {

    private String fullAddress;
    private double latitude;
    private double longitude;
}
