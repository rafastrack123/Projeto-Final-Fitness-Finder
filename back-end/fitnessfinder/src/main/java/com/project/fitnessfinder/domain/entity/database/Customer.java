package com.project.fitnessfinder.domain.entity.database;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Customer extends Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    //TODO: check is biretional mapping is needed for evaluations and leads
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "fromCustomer")
    private List<Evaluation> evaluations;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<Lead> leads;

    @ManyToOne
    private Objective objective;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    private List<VendorProposition> vendorPropositions;

}
