package com.project.fitnessfinder.domain.entity.database;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
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
