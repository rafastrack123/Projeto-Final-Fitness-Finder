package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import org.springframework.data.jpa.repository.JpaRepository;

//TODO: Check if is possible to remove VendorOfferRepositoryCustom interface
public interface VendorOfferRepository extends JpaRepository<VendorOffer, Long>, VendorOfferRepositoryCustom {

}
