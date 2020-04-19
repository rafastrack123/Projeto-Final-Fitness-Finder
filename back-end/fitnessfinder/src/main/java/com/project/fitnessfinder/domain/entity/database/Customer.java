package com.project.fitnessfinder.domain.entity.database;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Customer extends Person {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fromCustomer")
    private List<Evaluation> evaluations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Lead> leads;
}
