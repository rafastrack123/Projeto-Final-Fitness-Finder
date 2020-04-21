package com.project.fitnessfinder.repository;

import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;


public interface VendorOfferRepository extends JpaRepository<VendorOffer,Long> {


   // List<VendorOffer> findBooksByAuthorNameAndTitle(String authorName, String title);


}
