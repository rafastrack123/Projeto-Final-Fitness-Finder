package com.project.fitnessfinder.domain.entity.database;

import java.util.Date;
import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@MappedSuperclass
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;

    private String email;
    private String password;

    @Embedded
    private ContactInfo contactInfo;

    @Embedded
    private Address address;

    @CreatedDate
    private Date creationDate;

    @LastModifiedDate
    private Date updateDate;
}
