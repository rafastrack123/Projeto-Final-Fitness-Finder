package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VendorOfferRepository extends PagingAndSortingRepository<VendorOffer, Long> {
}
