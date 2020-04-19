package com.project.fitnessfinder.domain.entity.database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import lombok.Data;

@Data
@Entity
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Max(5)
    private Integer rating;

    @Lob
    @Column(length = 200)
    private String description;

    private boolean customerConfirmation;

    private boolean vendorConfirmation;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer fromCustomer;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor toVendor;

}
