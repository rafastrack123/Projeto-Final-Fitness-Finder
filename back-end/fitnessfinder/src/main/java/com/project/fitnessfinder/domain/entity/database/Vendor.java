package com.project.fitnessfinder.domain.entity.database;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Vendor extends Person {

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vendor")
    private List<VendorOffer> vendorOffers;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "toVendor")
    private List<Evaluation> evaluations;

    @OneToOne
    private Resume resume;

}
