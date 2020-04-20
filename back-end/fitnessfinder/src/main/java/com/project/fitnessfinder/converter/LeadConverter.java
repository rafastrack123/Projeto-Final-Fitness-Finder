package com.project.fitnessfinder.converter;

import com.project.fitnessfinder.domain.entity.api.LeadJson;
import com.project.fitnessfinder.domain.entity.database.Lead;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LeadConverter {

    public List<LeadJson> convert(List<Lead> leads){
        var leadJsonList = new ArrayList<LeadJson>();


        return leadJsonList;
    }
}
