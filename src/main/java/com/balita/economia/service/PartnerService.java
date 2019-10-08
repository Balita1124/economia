package com.balita.economia.service;


import com.balita.economia.model.Partner;
import com.balita.economia.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartnerService {

    @Autowired
    PartnerRepository partnerRepository;

    public Page<Partner> partnerList(int page, int siza) {

        Pageable pageable = PageRequest.of(page, siza);

        return partnerRepository.findAll(pageable);
    }

    public Partner createPartner(Partner newPartner) {
        return partnerRepository.save(newPartner);
    }
}
