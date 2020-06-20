package com.project.fitnessfinder.repository.implementation;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.VendorOfferJson;
import com.project.fitnessfinder.domain.entity.database.Address;
import com.project.fitnessfinder.repository.VendorOfferRepositoryCustom;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
@Slf4j
public class VendorOfferRepositoryImpl implements VendorOfferRepositoryCustom {

    private final EntityManager em;
    private final Converter converter;

    @Override
    public List<VendorOfferJson> searchVendorOffersByFilters(@NotNull Long serviceAreaId,
                                                             Long serviceGroupId,
                                                             Long serviceDetailId,
                                                             String vendorFirstName,
                                                             String vendorLastName,
                                                             BigDecimal maxPrice,
                                                             Boolean isHomeService,
                                                             Boolean isFirstClassFree,
                                                             Boolean isRemoteService,
                                                             Long maxDistanceInKm,
                                                             Address customerAddress,
                                                             String dayOfWeek,
                                                             String startTime,
                                                             String endTime) {

        var querySb = new StringBuilder("  SELECT vendor_offer.*, service_area.name as areaName, \n" +
                "    service_group.name as groupName, service_detail.name as detailName, \n" +
                "vendor.first_name as vendorFirstName, vendor.last_name as vendorLastName, vendor.resume as resume, \n" +
                String.format(" round(st_distance_sphere( POINT( vendor.latitude, vendor.longitude) , POINT(%s,%s ))/1000) AS distance \n",
                        customerAddress.getLatitude(), customerAddress.getLongitude()) +
                "        FROM vendor_offer \n" +
                "        JOIN vendor ON vendor_offer.vendor_id = vendor.id \n" +
                "        JOIN service_detail  on vendor_offer.service_detail_id = service_detail.id \n" +
                "        JOIN service_group on service_detail.service_group_id = service_group.id \n" +
                "        JOIN service_area on service_group.service_area_id = service_area.id \n");

        // available_schedule Join
        addAvailableSchedule(querySb, dayOfWeek, startTime, endTime);

        // Service Clauses
        addServiceAreaClause(querySb, serviceAreaId);
        addServiceGroupClause(querySb, serviceGroupId);
        addServiceDetailClause(querySb, serviceDetailId);

        //Vendor Names Clauses
        addFirstNameClause(querySb, vendorFirstName);
        addLastNameClause(querySb, vendorLastName);

        // Vendor Offer Clauses
        addMaxPriceClause(querySb, maxPrice);

        addIsFirstClassFreeClause(querySb, isFirstClassFree);
        addIsHomeServiceClause(querySb, isHomeService);
        addIsRemoteServiceClause(querySb, isRemoteService);

        //Distance Clause
        addMaxDistanceClause(querySb, customerAddress, maxDistanceInKm);

        // Schedule
        addDayOfWeek(querySb, dayOfWeek);
        addStartTimeClause(querySb, startTime);
        addEndTimeClause(querySb, endTime);

        // Add group by to remove duplicates
        querySb.append("    GROUP BY vendor_offer.id;");

        var query = querySb.toString();

        log.info("Vendor Offer Query:");
        log.info(query);

        Query q = em.createNativeQuery(query, Tuple.class);

        List<Tuple> tupleList = q.getResultList();

        return tupleToVendorOfferJson(tupleList);
    }

    private void addAvailableSchedule(StringBuilder querySb, String dayOfWeek, String startTime, String endTime) {

        if (StringUtils.isNotBlank(dayOfWeek) ||
                StringUtils.isNotBlank(startTime) ||
                StringUtils.isNotBlank(endTime)) {
            var join = "    JOIN available_schedule on vendor_offer.id = available_schedule.vendor_offer_id \n";

            querySb.append(join);
        }
    }

    private void addServiceAreaClause(StringBuilder querySb, Long serviceAreaId) {
        if (serviceAreaId != null) {
            var clause = String.format(" AND service_area.id = %s \n", serviceAreaId);
            querySb.append(clause);
        }

    }

    private void addServiceGroupClause(StringBuilder querySb, Long serviceGroupId) {
        if (serviceGroupId != null) {
            var clause = String.format(" AND service_group.id= %s \n", serviceGroupId);

            querySb.append(clause);
        }
    }

    private void addServiceDetailClause(StringBuilder querySb, Long serviceDetailId) {
        if (serviceDetailId != null) {
            var clause = String.format(" AND service_detail.id= %s \n", serviceDetailId);

            querySb.append(clause);
        }
    }

    private void addFirstNameClause(StringBuilder querySb, String firstName) {

        if (StringUtils.isNotBlank(firstName)) {
            var clause = String.format(" AND vendor.first_name LIKE '%s' \n", firstName);

            querySb.append(clause);
        }
    }

    private void addLastNameClause(StringBuilder querySb, String lastName) {
        if (StringUtils.isNotBlank(lastName)) {
            var clause = String.format(" AND vendor.last_name LIKE '%s' \n", lastName);

            querySb.append(clause);
        }
    }

    private void addMaxPriceClause(StringBuilder querySb, BigDecimal price) {
        if (price != null) {
            var clause = String.format("  AND vendor_offer.price <= %s \n", price);

            querySb.append(clause);
        }
    }

    private void addIsHomeServiceClause(StringBuilder querySb, Boolean isHomeService) {
        if (isHomeService) {
            var clause = "  AND vendor_offer.is_home_service = true \n";

            querySb.append(clause);
        }
    }

    private void addIsRemoteServiceClause(StringBuilder querySb, Boolean isRemoteService) {
        if (isRemoteService) {
            var clause = "  AND vendor_offer.is_remote_service = true \n";

            querySb.append(clause);
        }
    }

    private void addIsFirstClassFreeClause(StringBuilder querySb, Boolean isFirstClassFree) {
        if (isFirstClassFree) {
            var clause = "   AND vendor_offer.first_class_free = true \n";

            querySb.append(clause);
        }
    }

    private void addMaxDistanceClause(StringBuilder querySb, Address customerAddress, Long maxDistanceInKm) {

        if (maxDistanceInKm != null && customerAddress != null) {
            var clause = String.format(" HAVING distance <= %s \n", maxDistanceInKm);

            querySb.append(clause);
        }
    }

    private void addDayOfWeek(StringBuilder querySb, String dayOfWeek) {
        if (StringUtils.isNotBlank(dayOfWeek)) {
            var dayOfWeekInt = converter.convertDayInPtBrToInt(dayOfWeek);
            var clause = String.format("    AND available_schedule.day_of_week = %s \n", dayOfWeekInt);

            querySb.append(clause);
        }
    }

    private void addStartTimeClause(StringBuilder querySb, String startTime) {
        if (startTime != null) {

            var clause = String.format("   AND available_schedule.start_time = '%s' \n", converter.addSeconds(startTime));
            querySb.append(clause);
        }
    }

    private void addEndTimeClause(StringBuilder querySb, String endTime) {
        if (endTime != null) {
            var clause = String.format("   AND available_schedule.end_time = '%s' \n", converter.addSeconds(endTime));

            querySb.append(clause);
        }
    }

    private List<VendorOfferJson> tupleToVendorOfferJson(List<Tuple> tupleList) {
        var vendorOfferJsonList = new ArrayList<VendorOfferJson>();

        for (Tuple tuple : tupleList) {


            var vendorOfferJson = new VendorOfferJson();

            vendorOfferJson.id = tuple.get("id", BigInteger.class).longValue();

            vendorOfferJson.firstClassFree = tuple.get("first_class_free", Boolean.class);
            vendorOfferJson.isHomeService = tuple.get("is_home_service", Boolean.class);
            vendorOfferJson.isRemoteService = tuple.get("is_remote_service", Boolean.class);

            vendorOfferJson.price = tuple.get("price", BigDecimal.class);

            vendorOfferJson.serviceDescription = tuple.get("service_description", String.class);

            vendorOfferJson.vendorFirstName = tuple.get("vendorFirstName", String.class);
            vendorOfferJson.vendorLastName = tuple.get("vendorLastName", String.class);
            vendorOfferJson.resume = tuple.get("resume", String.class);

            vendorOfferJson.areaName = tuple.get("areaName", String.class);
            vendorOfferJson.groupName = tuple.get("groupName", String.class);
            vendorOfferJson.detailName = tuple.get("detailName", String.class);

            vendorOfferJson.distance = tuple.get("distance", Double.class);

            vendorOfferJson.imageUrl = tuple.get("image_url", String.class);

            vendorOfferJsonList.add(vendorOfferJson);
        }

        return vendorOfferJsonList;
    }

}
