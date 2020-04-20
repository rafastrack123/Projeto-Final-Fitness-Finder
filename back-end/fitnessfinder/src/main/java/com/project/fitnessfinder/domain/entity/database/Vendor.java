package com.project.fitnessfinder.domain.entity.database;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Vendor extends Person {

    @Lob
    @Column(length = 400)
    private String resume;

    @OneToMany(mappedBy = "vendor")
    private List<VendorOffer> vendorOffers;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "toVendor")
    private List<Evaluation> evaluations;

}
