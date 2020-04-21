package com.project.fitnessfinder.bootstrap;

import com.project.fitnessfinder.domain.entity.database.Address;
import com.project.fitnessfinder.domain.entity.database.ContactInfo;
import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Lead;
import com.project.fitnessfinder.domain.entity.database.Objective;
import com.project.fitnessfinder.domain.entity.database.ServiceArea;
import com.project.fitnessfinder.domain.entity.database.ServiceDetail;
import com.project.fitnessfinder.domain.entity.database.ServiceGroup;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.repository.AvailableScheduleRepository;
import com.project.fitnessfinder.repository.CustomerRepository;
import com.project.fitnessfinder.repository.EvaluationRepository;
import com.project.fitnessfinder.repository.LeadRepository;
import com.project.fitnessfinder.repository.ObjectiveRepository;
import com.project.fitnessfinder.repository.ServiceAreaRepository;
import com.project.fitnessfinder.repository.ServiceDetailRepository;
import com.project.fitnessfinder.repository.ServiceGroupRepository;
import com.project.fitnessfinder.repository.VendorOfferRepository;
import com.project.fitnessfinder.repository.VendorRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("Dev")
public class FitnessFinderLoader implements CommandLineRunner {

    private final AvailableScheduleRepository availableScheduleRepository;
    private final CustomerRepository customerRepository;
    private final EvaluationRepository evaluationRepository;
    private final LeadRepository leadRepository;
    private final ObjectiveRepository objectiveRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final ServiceDetailRepository serviceDetailRepository;
    private final ServiceGroupRepository serviceGroupRepository;
    private final VendorOfferRepository vendorOfferRepository;
    private final VendorRepository vendorRepository;

    //attributes
    @Override
    public void run(String... args) {

        System.out.println("Executing System Bootstrap");

        loadData();

        System.out.println("System Bootstrap Was Executed successfully");
    }

    private void loadData() {
        var objectives = buildObjectives();
        var details = buildServiceDetails();


        var customer = new Customer();

        customer.setFirstName("Ronaldo");
        customer.setLastName("Fenomeno");

        customer.setContactInfo(buildContactInfo());
        customer.setAddress(buildAddress());

        customer.setObjective(objectives.get(0));

        customer = customerRepository.save(customer);


        var vendor = new Vendor();

        vendor.setFirstName("Ronaldo");
        vendor.setLastName("Fenomeno");

        vendor.setContactInfo(buildContactInfo());
        vendor.setAddress(buildAddress());

        vendor = vendorRepository.save(vendor);

        var vendorOffer = VendorOffer.builder()
                .vendor(vendor)
                .serviceDetail(details.get(0))
                .build();

        vendorOffer = vendorOfferRepository.save(vendorOffer);

        var lead = Lead.builder()
                .customer(customer)
                .vendor(vendor)
                .strongLead(true)
                .build();


        lead = leadRepository.save(lead);

    }

    private List<Objective> buildObjectives() {
        var objectives = new ArrayList<Objective>();

        objectives.add(buildObjective("Perder de peso"));
        objectives.add(buildObjective("Ganhar massa"));
        objectives.add(buildObjective("Condicionamento"));
        objectives.add(buildObjective("Hipertrofia"));

        objectiveRepository.saveAll(objectives);

        return objectives;
    }

    private Objective buildObjective(String name) {
        return Objective.builder()
                .name(name)
                .build();
    }

    private List<ServiceDetail> buildServiceDetails() {
        // Areas
        var areasList = new ArrayList<ServiceArea>();

        var fitnessArea = buildServiceArea("Fitness");
        var eatingArea = buildServiceArea("Alimentação");
        var wellnessArea = buildServiceArea("Bem-estar");

        areasList.add(fitnessArea);
        areasList.add(eatingArea);
        areasList.add(wellnessArea);

        serviceAreaRepository.saveAll(areasList);


        //Groups
        var groupsList = new ArrayList<ServiceGroup>();

        var fightingGroup = buildServiceGroup("Artes Marciais", fitnessArea);
        var exerciseGroup = buildServiceGroup("Exercícios", fitnessArea);

        var nutritionGroup = buildServiceGroup("Nutrição", eatingArea);

        var yogaGroup = buildServiceGroup("Yoga", wellnessArea);
        var pilatesGroup = buildServiceGroup("Pilates", wellnessArea);

        groupsList.add(fightingGroup);
        groupsList.add(exerciseGroup);
        groupsList.add(nutritionGroup);
        groupsList.add(yogaGroup);
        groupsList.add(pilatesGroup);

        serviceGroupRepository.saveAll(groupsList);

        //Details
        var details = new ArrayList<ServiceDetail>();

        details.add(buildServiceDetails("Boxe", fightingGroup));
        details.add(buildServiceDetails("Muay Thai", fightingGroup));
        details.add(buildServiceDetails("Kickboxing", fightingGroup));
        details.add(buildServiceDetails("MMA", fightingGroup));

        details.add(buildServiceDetails("Musculação", exerciseGroup));
        details.add(buildServiceDetails("Spinning", exerciseGroup));
        details.add(buildServiceDetails("Crossfit", exerciseGroup));
        details.add(buildServiceDetails("Funcional", exerciseGroup));

        details.add(buildServiceDetails("Tradicional", nutritionGroup));
        details.add(buildServiceDetails("Ortomolecular", nutritionGroup));

        details.add(buildServiceDetails("Hatha", yogaGroup));
        details.add(buildServiceDetails("Raja", yogaGroup));
        details.add(buildServiceDetails("Bhakti", yogaGroup));


        details.add(buildServiceDetails("Original", pilatesGroup));
        details.add(buildServiceDetails("Contemporâneo", pilatesGroup));
        details.add(buildServiceDetails("Funcional", pilatesGroup));

        serviceDetailRepository.saveAll(details);

        return details;

    }

    private ServiceArea buildServiceArea(String name) {

        return ServiceArea.builder().name(name).build();

    }

    private ServiceGroup buildServiceGroup(String name, ServiceArea serviceArea) {

        return ServiceGroup.builder()
                .name(name)
                .serviceArea(serviceArea)
                .build();
    }

    private ServiceDetail buildServiceDetails(String name, ServiceGroup serviceGroup) {
        return ServiceDetail.builder()
                .name(name)
                .serviceGroup(serviceGroup)
                .build();

    }


    private ContactInfo buildContactInfo() {
        var contact = new ContactInfo();

        contact.setEmail("teste@hotmail.com");
        contact.setCellphone("51992465588");
        contact.setLinkToFacebook("facebook.com/Customer");

        return contact;
    }

    private Address buildAddress() {
        var address = new Address();

        address.setFullAddress("Rua Piauí 40");
        address.setLatitude(99L);
        address.setLongitude(99L);

        return address;

    }

}

