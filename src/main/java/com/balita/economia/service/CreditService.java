package com.balita.economia.service;

import com.balita.economia.model.Credit;
import com.balita.economia.repository.CreditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CreditService {

    @Autowired
    CreditRepository creditRepository;

    public Page<Credit> creditPagedList(PageRequest pageable, String keyword) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Credit> credits = (keyword == null) ? (List<Credit>) creditRepository.findAll() : creditRepository.findAllByPartnerNameContains(keyword);
        List<Credit> list;

        if (credits.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, credits.size());
            list = credits.subList(startItem, toIndex);
        }

        Page<Credit> creditPage
                = new PageImpl<Credit>(list, PageRequest.of(currentPage, pageSize), credits.size());

        return creditPage;
    }

    public Credit saveCredit(Credit newCredit) {
        return creditRepository.save(newCredit);
    }

    public Credit findCreditById(Long creditId) {
        return creditRepository.findById(creditId).orElse(null);
    }
}
