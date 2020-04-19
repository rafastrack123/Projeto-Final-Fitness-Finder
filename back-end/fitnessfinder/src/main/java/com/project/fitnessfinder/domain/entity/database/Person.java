package com.project.fitnessfinder.domain.entity.database;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Data
@Entity
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private Date birthDay;
    private String cellphone;
    private String linkToFacebook;

    private String address;
    private Long latitude;
    private Long longitude;

    @OneToMany(fetch = LAZY, mappedBy = "fromCustomer")
    private List<Evaluation> evaluations;

    @CreatedDate
    private Date creationDate;
    @LastModifiedDate
    private Date updateDate;
}
