package com.project.fitnessfinder.service;

import com.project.fitnessfinder.converter.Converter;
import com.project.fitnessfinder.domain.entity.api.EvaluationRequestJson;
import com.project.fitnessfinder.domain.entity.database.EvaluationRequest;
import com.project.fitnessfinder.repository.EvaluationRequestRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EvaluationRequestService {

    private final Converter converter;
    private final CustomerService customerService;
    private final LeadService leadService;
    private final VendorOfferService vendorOfferService;
    private final EvaluationRequestRepository evaluationRequestRepository;


    public void createEvaluationRequest(Long customerId, Long vendorOfferId, Long leadId) {

        var customer = customerService.get(customerId);
        var vendorOffer = vendorOfferService.get(vendorOfferId);

        var evaluationRequest = new EvaluationRequest();

        evaluationRequest.setCustomer(customer);
        evaluationRequest.setVendorOffer(vendorOffer);

        leadService.deleteById(leadId);
        evaluationRequestRepository.save(evaluationRequest);
    }

    public List<EvaluationRequestJson> getByCustomerId(Long id) {
        List<EvaluationRequest> evaluationRequestList = evaluationRequestRepository.getByCustomerId(id);

        return evaluationRequestList.stream()
                .map(converter::convert)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id){
        evaluationRequestRepository.deleteById(id);
    }
}
