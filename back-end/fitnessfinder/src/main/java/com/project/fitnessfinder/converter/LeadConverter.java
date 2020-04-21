package com.project.fitnessfinder.converter;

import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.domain.entity.database.Lead;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeadConverter {

    private final ContactConverter contactConverter;

    public List<LeadJson> convert(List<Lead> leads) {


        return leads.stream()
                .map(this::convert)
                .collect(Collectors.toList());
    }

    public LeadJson convert(Lead lead) {
        var json = new LeadJson();

        json.customerFirstName = lead.getCustomer().getFirstName();
        json.customerLastName = lead.getCustomer().getLastName();

        json.isStrongLead = lead.isStrongLead();

        json.customerContact = contactConverter.convert(lead.getCustomer().getContactInfo());

        return json;
    }
}
