package com.balita.economia.service;


import com.balita.economia.model.Partner;
import com.balita.economia.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PartnerService {

    @Autowired
    PartnerRepository partnerRepository;

    public Page<Partner> partnerPagedList(Pageable pageable, String keyword) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Partner> partners = (keyword == null) ? (List<Partner>) partnerRepository.findAll() : partnerRepository.findAllByFirstnameContainsOrLastnameContains(keyword, keyword);
        List<Partner> list;

        if (partners.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, partners.size());
            list = partners.subList(startItem, toIndex);
        }

        Page<Partner> partnerPage
                = new PageImpl<Partner>(list, PageRequest.of(currentPage, pageSize), partners.size());

        return partnerPage;
    }

    public Partner savePartner(Partner newPartner) {
        return partnerRepository.save(newPartner);
    }

    public Partner findPartnerById(Long partnerId) {
        return partnerRepository.findById(partnerId).orElse(null);
    }

    public void deletePartner(Partner partner) {
        partnerRepository.delete(partner);
    }
}
