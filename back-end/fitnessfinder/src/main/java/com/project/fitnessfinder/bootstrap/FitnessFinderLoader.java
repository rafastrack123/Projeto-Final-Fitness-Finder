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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
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

    @Override
    public void run(String... args) {

        System.out.println("Executing System Bootstrap");

        loadData();

        System.out.println("System Bootstrap Was Executed successfully");
    }

    private void loadData() {
        var random = new Random();// To get Random element of list

        var objectives = buildObjectives();
        var details = buildServiceDetails();


        var randomObjectiveOne = objectives.get(random.nextInt(objectives.size()));
        var customerLebron = buildCustomer("Lebron", "James", "lebron.james@gmail.com",
                "password", randomObjectiveOne);

        var randomObjetiveTwo = objectives.get(random.nextInt(objectives.size()));
        var customerRonaldo = buildCustomer("Ronaldo", "Fenomeno", "ronaldo@gmail.com",
                "password", randomObjetiveTwo);

        var randomObjetiveThree = objectives.get(random.nextInt(objectives.size()));
        var customerObama = buildCustomer("Barack", "Obama", "barack.obama@gmail.com",
                "password", randomObjetiveThree);


        var vendorBrady = buildVendor("Tom", "Brady", "tom.brady@gmail.com", "password");

        var vendorSnow = buildVendor("John", "Snow", "john.snow@gmail.com", "password");

        var vendorWhite = buildVendor("Walter", "White", "walter.white@gmail.com", "password");


        var vendorOfferBrady = buildVendorOffer(vendorBrady, new BigDecimal("25"), "Descricao do serviço",
                details.get(random.nextInt(details.size())));
        var vendorOfferBradyTwo = buildVendorOffer(vendorBrady, new BigDecimal("50"), "Descricao do serviço",
                details.get(random.nextInt(details.size())));

        var vendorOfferSnow = buildVendorOffer(vendorSnow, new BigDecimal("99.99"), "Descricao do serviço",
                details.get(random.nextInt(details.size())));
        var vendorOfferTwo = buildVendorOffer(vendorSnow, new BigDecimal("36"), "Descricao do serviço",
                details.get(random.nextInt(details.size())));

        var vendorOfferWhite = buildVendorOffer(vendorWhite, new BigDecimal("40"), "Descricao do serviço",
                details.get(random.nextInt(details.size())));
        var vendorOfferWhiteTwo = buildVendorOffer(vendorWhite, new BigDecimal("25"), "Descricao do serviço",
                details.get(random.nextInt(details.size())));

        var strongLead = buildLead(customerLebron, vendorOfferBrady, true);

        var weakLead = buildLead(customerRonaldo, vendorOfferTwo, false);
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

    private Customer buildCustomer(String firstName, String lastName, String email, String password, Objective objective) {
        var customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        customer.setEmail(email);
        customer.setPassword(password);

        customer.setContactInfo(buildContactInfo());
        customer.setAddress(buildAddressCustomer());

        customer.setObjective(objective);

        return customerRepository.save(customer);
    }

    private Vendor buildVendor(String firstname, String lastName, String email, String password) {
        var vendor = new Vendor();

        vendor.setFirstName(firstname);
        vendor.setLastName(lastName);

        vendor.setEmail(email);
        vendor.setPassword(password);

        vendor.setContactInfo(buildContactInfo());
        vendor.setAddress(buildAddressVendor());

        return vendorRepository.save(vendor);
    }


    private ContactInfo buildContactInfo() {
        var contact = new ContactInfo();

        contact.setCellphone("51992465588");

        return contact;
    }

    private Address buildAddressCustomer() {
        var address = new Address();

        address.setFullAddress("Rua Piauí 40");
        address.setLatitude(-30.0077714);
        address.setLongitude(-51.1769566);

        return address;
    }

    private Address buildAddressVendor() {
        var address = new Address();

        address.setFullAddress("Estádio Beira-Rio");
        address.setLatitude(-30.0654331);
        address.setLongitude(-51.235908);

        return address;
    }

    private VendorOffer buildVendorOffer(Vendor vendor, BigDecimal price, String serviceDescription, ServiceDetail detail) {

        var vendorOffer = VendorOffer.builder()
                .vendor(vendor)
                .price(price)
                .serviceDescription(serviceDescription)
                .serviceDetail(detail)
                .build();

        return vendorOfferRepository.save(vendorOffer);

    }

    private Lead buildLead(Customer customer, VendorOffer vendorOffer, boolean isStrongLead) {

        var lead = Lead.builder()
                .customer(customer)
                .vendorOffer(vendorOffer)
                .isStrongLead(isStrongLead)
                .updateDate(new Date())
                .build();

        return leadRepository.save(lead);
    }

}

