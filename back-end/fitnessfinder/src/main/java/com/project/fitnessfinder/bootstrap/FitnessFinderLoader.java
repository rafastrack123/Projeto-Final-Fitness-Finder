package com.project.fitnessfinder.bootstrap;

import com.project.fitnessfinder.domain.entity.database.Address;
import com.project.fitnessfinder.domain.entity.database.AvailableSchedule;
import com.project.fitnessfinder.domain.entity.database.ContactInfo;
import com.project.fitnessfinder.domain.entity.database.Customer;
import com.project.fitnessfinder.domain.entity.database.Lead;
import com.project.fitnessfinder.domain.entity.database.Objective;
import com.project.fitnessfinder.domain.entity.database.ServiceArea;
import com.project.fitnessfinder.domain.entity.database.ServiceDetail;
import com.project.fitnessfinder.domain.entity.database.ServiceGroup;
import com.project.fitnessfinder.domain.entity.database.Vendor;
import com.project.fitnessfinder.domain.entity.database.VendorOffer;
import com.project.fitnessfinder.domain.entity.database.VendorProposition;
import com.project.fitnessfinder.repository.AvailableScheduleRepository;
import com.project.fitnessfinder.repository.CustomerRepository;
import com.project.fitnessfinder.repository.EvaluationRepository;
import com.project.fitnessfinder.repository.LeadRepository;
import com.project.fitnessfinder.repository.ObjectiveRepository;
import com.project.fitnessfinder.repository.ServiceAreaRepository;
import com.project.fitnessfinder.repository.ServiceDetailRepository;
import com.project.fitnessfinder.repository.ServiceGroupRepository;
import com.project.fitnessfinder.repository.VendorOfferRepository;
import com.project.fitnessfinder.repository.VendorPropositionRepository;
import com.project.fitnessfinder.repository.VendorRepository;
import java.math.BigDecimal;
import java.time.DayOfWeek;
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
    private final VendorPropositionRepository vendorPropositionRepository;

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

        var addressList = buildAddressList();

        var randomObjectiveOne = objectives.get(random.nextInt(objectives.size()));
        var customerLebron = buildCustomer("Lebron", "James", "lebron.james@gmail.com",
                "5f4dcc3b5aa765d61d8327deb882cf99", randomObjectiveOne, addressList.get(0));

        var randomObjetiveTwo = objectives.get(random.nextInt(objectives.size()));
        var customerRonaldo = buildCustomer("Ronaldo", "Fenomeno", "ronaldo@gmail.com",
                "5f4dcc3b5aa765d61d8327deb882cf99", randomObjetiveTwo, addressList.get(1));

        var randomObjetiveThree = objectives.get(random.nextInt(objectives.size()));
        var customerObama = buildCustomer("Barack", "Obama", "barack.obama@gmail.com",
                "5f4dcc3b5aa765d61d8327deb882cf99", randomObjetiveThree, addressList.get(2));


        var vendorBrady = buildVendor("Tom", "Brady", "tom.brady@gmail.com",
                "5f4dcc3b5aa765d61d8327deb882cf99", addressList.get(3));

        var vendorSnow = buildVendor("John", "Snow", "john.snow@gmail.com",
                "5f4dcc3b5aa765d61d8327deb882cf99", addressList.get(4));

        var vendorWhite = buildVendor("Walter", "White", "walter.white@gmail.com",
                "5f4dcc3b5aa765d61d8327deb882cf99", addressList.get(5));

        var vendorOfferBrady = buildVendorOffer(vendorBrady, new BigDecimal("25"), "Descricao do serviço",
                details.get(random.nextInt(details.size())), "https://zh.rbsdirect.com.br/imagesrc/23027367.jpg?w=700");
        var vendorOfferBradyTwo = buildVendorOffer(vendorBrady, new BigDecimal("50"), "Descricao do serviço",
                details.get(random.nextInt(details.size())),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcS-Nk0agHUcpiONDre-UZUKrvqPhnbnauccld0TE3kiI3rzmmCm&usqp=CAU");

        var vendorOfferSnow = buildVendorOffer(vendorSnow, new BigDecimal("99.99"), "Descricao do serviço",
                details.get(random.nextInt(details.size())),
                "https://rollingstone.uol.com.br/media/_versions/jon_snow_widelg.jpg");

        var vendorOfferTwo = buildVendorOffer(vendorSnow, new BigDecimal("36"), "Descricao do serviço",
                details.get(random.nextInt(details.size())), "https://imagens.canaltech.com.br/celebridades/487.400.jpg");

        var vendorOfferWhite = buildVendorOffer(vendorWhite, new BigDecimal("40"), "Descricao do serviço",
                details.get(random.nextInt(details.size())),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQU45maD-w8VhMemWok7BE5DwBVWZQ-knELSezeJDWLYn7_tcsE&usqp=CAU");

        var vendorOfferWhiteTwo = buildVendorOffer(vendorWhite, new BigDecimal("25"), "Descricao do serviço",
                details.get(random.nextInt(details.size())),
                "https://www.jornalopcao.com.br/wp-content/uploads/2018/09/walter-white.jpg");

        var strongLead = buildLead(customerObama, vendorOfferBrady, true);

        var weakLead = buildLead(customerRonaldo, vendorOfferBradyTwo, false);

        buildVendorProposition(customerLebron, vendorOfferSnow,
                "Boa tarde Lebron, vi que está procurando um serviço que ofereço, posso te dar 30% de desconto no primeiro mês.");

        buildVendorProposition(customerLebron, vendorOfferWhite,
                "Olá Lebron, gostaría de ter aula de boxe? Podemos fechar um pacote semestral com pela metade do preço do concorrente.");

        buildVendorProposition(customerLebron, vendorOfferBrady,
                "Vi seu interesse, gostaria de te fornecer duas aulas experimentais gratuitas. O que acha podemos negociar?");

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

    private Customer buildCustomer(String firstName, String lastName, String email, String password, Objective objective,
                                   Address address) {
        var customer = new Customer();

        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        customer.setEmail(email);
        customer.setPassword(password);

        customer.setContactInfo(buildContactInfo());
        customer.setAddress(address);

        customer.setObjective(objective);

        customer.setPreferredContact("WhatsApp");

        return customerRepository.save(customer);
    }

    private Vendor buildVendor(String firstname, String lastName, String email, String password, Address address) {
        var vendor = new Vendor();

        vendor.setFirstName(firstname);
        vendor.setLastName(lastName);

        vendor.setEmail(email);
        vendor.setPassword(password);

        vendor.setContactInfo(buildContactInfo());
        vendor.setAddress(address);

        return vendorRepository.save(vendor);
    }

    private List<Address> buildAddressList() {
        var addressList = new ArrayList<Address>();

        addressList.add(buildAddress(-30.0654331, -51.235908, "Estádio Beira-Rio"));
        addressList.add(buildAddress(-30.0077714, -51.1769566, "Rua Piauí 40"));

        addressList.add(buildAddress(-30.0596973, -51.1738309, "Tecnopuc"));
        addressList.add(buildAddress(-29.9739581, -51.1970725, "Arena Grêmio"));

        addressList.add(buildAddress(-30.0368901, -51.23005, "Faculdade Senac Campus II"));
        addressList.add(buildAddress(-30.0271233, -51.2029356, "Parque Moinhos de Vento"));

        return addressList;
    }


    private ContactInfo buildContactInfo() {
        var contact = new ContactInfo();

        contact.setCellphone("51992465588");
        contact.setFacebook("Facebook do herói");
        contact.setInstagram("User do instagram");
        return contact;
    }

    private Address buildAddress(double lat, double longitude, String fullAddress) {
        var address = new Address();

        address.setLatitude(lat);
        address.setLongitude(longitude);

        address.setFullAddress("Estádio Beira-Rio");

        return address;
    }

    private VendorOffer buildVendorOffer(Vendor vendor, BigDecimal price, String serviceDescription, ServiceDetail detail,
                                         String imageUrl) {

        var vendorOffer = VendorOffer.builder()
                .vendor(vendor)
                .price(price)
                .serviceDescription(serviceDescription)
                .serviceDetail(detail)
                .homeService(Math.random() < 0.5)
                .remoteService(Math.random() < 0.5)
                .firstClassFree(Math.random() < 0.5)
                .imageUrl(imageUrl)
                .build();

        var availableSchedules = buildAvailableSchedules(vendorOffer);
        vendorOffer.setAvailableSchedule(availableSchedules);


        vendorOffer = vendorOfferRepository.save(vendorOffer);

        availableScheduleRepository.saveAll(availableSchedules);

        return vendorOffer;
    }

    private List<AvailableSchedule> buildAvailableSchedules(VendorOffer vendorOffer) {

        var availableSchedule1 = buildAvailableSchedule(vendorOffer, DayOfWeek.MONDAY, "12:00:00", "13:00:00");
        var availableSchedule2 = buildAvailableSchedule(vendorOffer, DayOfWeek.MONDAY, "14:00:00", "15:00:00");
        var availableSchedule3 = buildAvailableSchedule(vendorOffer, DayOfWeek.TUESDAY, "12:00:00", "13:00:00");

        return List.of(availableSchedule1, availableSchedule2, availableSchedule3);
    }

    private AvailableSchedule buildAvailableSchedule(VendorOffer vendorOffer, DayOfWeek dayOfWeek, String startTime,
                                                     String endTime) {
        return AvailableSchedule.builder()
                .vendorOffer(vendorOffer)
                .dayOfWeek(dayOfWeek)
                .startTime(startTime)
                .endTime(endTime)
                .build();
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

    private void buildVendorProposition(Customer customerLebron, VendorOffer vendorOfferSnow, String message) {
        var vendorProposition = new VendorProposition();

        vendorProposition.setCustomer(customerLebron);
        vendorProposition.setVendorOffer(vendorOfferSnow);

        vendorProposition.setMessage(message);

        vendorPropositionRepository.save(vendorProposition);
    }

}

