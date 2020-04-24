package com.project.fitnessfinder.repository.implementation;

import com.project.fitnessfinder.domain.entity.database.ServiceArea;
import com.project.fitnessfinder.domain.entity.database.ServiceDetail;
import com.project.fitnessfinder.domain.entity.database.ServiceGroup;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.repository.VendorOfferRepositoryCustom;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class VendorOfferRepositoryImpl implements VendorOfferRepositoryCustom {

    private final EntityManager em;

    @Override
    public List<VendorOffer> searchVendorOffersByFilter(Long serviceAreaId,
                                                        Long serviceGroupId,
                                                        Long serviceDetailId,
                                                        BigDecimal maxPrice,
                                                        String vendorName,
                                                        boolean isHomeService,
                                                        boolean firstClassFree) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<VendorOffer> cq = cb.createQuery(VendorOffer.class);


        Root<VendorOffer> vendorOffer = cq.from(VendorOffer.class);
        Join<VendorOffer, Vendor> vendor = vendorOffer.join("vendor");
        Join<VendorOffer, ServiceDetail> serviceDetail = vendorOffer.join("serviceDetail");
        Join<ServiceDetail, ServiceGroup> serviceGroup = serviceDetail.join("serviceGroup");
        Join<ServiceGroup, ServiceArea> serviceArea = serviceGroup.join("serviceArea");

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(serviceArea.get("id"), serviceAreaId)); // Service Id is required

        if (serviceGroupId != null) {
            predicates.add(cb.equal(serviceGroup.get("id"), serviceGroupId));
        }

        if (serviceDetailId != null) {
            predicates.add(cb.equal(serviceDetail.get("id"), serviceDetailId));
        }

        if (maxPrice != null) {
            predicates.add(cb.lessThan(vendorOffer.get("price"), maxPrice));
        }

        if (vendorName != null && StringUtils.isEmpty(vendorName.trim())) {
            predicates.add(cb.or(
                    cb.equal(vendor.get("firstName"), vendorName),
                    cb.equal(vendor.get("lastName"), vendorName)));
        }

        if (isHomeService) {
            predicates.add(cb.equal(vendorOffer.get("isHomeService"), true));
        }

        if (firstClassFree) {
            predicates.add(cb.equal(vendorOffer.get("firstClassFree"), true));
        }

        cq.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(cq).getResultList();
    }

}
