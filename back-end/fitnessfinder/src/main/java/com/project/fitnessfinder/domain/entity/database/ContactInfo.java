package com.project.fitnessfinder.domain.entity.database;

import javax.persistence.Embeddable;
import lombok.Data;


@Embeddable
@Data
public class ContactInfo {

    private String email;
    private String cellphone;
    private String linkToFacebook;

}
