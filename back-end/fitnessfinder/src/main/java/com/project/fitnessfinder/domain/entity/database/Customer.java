package com.project.fitnessfinder.domain.entity.database;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Customer {

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

}
