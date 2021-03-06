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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class VendorOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private BigDecimal price;

    private boolean homeService;

    private boolean remoteService;

    private boolean firstClassFree;

    private String serviceDescription;

    private String imageUrl;

    @ManyToOne
    private Vendor vendor;

    @ManyToOne
    private ServiceDetail serviceDetail;

    @OneToMany(mappedBy = "vendorOffer")
    private List<AvailableSchedule> availableSchedule;
}
