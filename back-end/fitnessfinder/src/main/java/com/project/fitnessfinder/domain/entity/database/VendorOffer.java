package com.project.fitnessfinder.domain.entity.database;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Entity;
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
public class VendorOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal price;

    private boolean isHomeService;

    private Long maxDistanceForHomeServiceInKms;

    private boolean firstClassFree;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private ServiceArea serviceArea;

    @ManyToOne
    private ServiceGroup serviceGroup;

    @ManyToOne
    private ServiceDetail serviceDetail;

    @OneToMany(mappedBy = "vendorOffer")
    private List<AvailableSchedule> availableSchedule;
}
