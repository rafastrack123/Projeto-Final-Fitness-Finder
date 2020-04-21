package com.project.fitnessfinder.converter;

import com.project.fitnessfinder.domain.entity.api.ContactJson;
import com.project.fitnessfinder.domain.entity.database.ContactInfo;
import org.springframework.stereotype.Component;

@Component
public class ContactConverter {

    public ContactJson convert(ContactInfo contact) {
        var contactJson = new ContactJson();

        contactJson.setCellphone(contact.getCellphone());
        contactJson.setEmail(contact.getEmail());
        contactJson.setLinkToFacebook(contact.getLinkToFacebook());

        return contactJson;
    }
}
