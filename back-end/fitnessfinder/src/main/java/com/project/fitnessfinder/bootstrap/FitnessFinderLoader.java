package com.project.fitnessfinder.bootstrap;

import com.project.fitnessfinder.repository.AvailableScheduleRepository;
import com.project.fitnessfinder.repository.CustomerRepository;
import com.project.fitnessfinder.repository.EvaluationRepository;
import com.project.fitnessfinder.repository.LeadRepository;
import com.project.fitnessfinder.repository.ServiceAreaRepository;
import com.project.fitnessfinder.repository.ServiceDetailRepository;
import com.project.fitnessfinder.repository.ServiceGroupRepository;
import com.project.fitnessfinder.repository.VendorOfferRepository;
import com.project.fitnessfinder.repository.VendorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FitnessFinderLoader implements CommandLineRunner {

    private final AvailableScheduleRepository availableScheduleRepository;
    private final CustomerRepository customerRepository;
    private final EvaluationRepository evaluationRepository;
    private final LeadRepository leadRepository;
    private final ServiceAreaRepository serviceAreaRepository;
    private final ServiceDetailRepository serviceDetailRepository;
    private final ServiceGroupRepository serviceGroupRepository;
    private final VendorOfferRepository vendorOfferRepository;
    private final VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("TAMO NO BAITA");


        System.out.println("DALE");
        System.out.println("DALE");
        System.out.println("DALE");System.out.println("DALE");
        System.out.println("DALE");
        System.out.println("DALE");
        System.out.println("DALE");
        System.out.println("DALE");System.out.println("DALE");


    }

    private void loadData() {

    }


}

