package com.balita.economia.service;


import com.balita.economia.model.Transaction;
import com.balita.economia.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public Page<Transaction> transactionPagedList(PageRequest pageable, String keyword) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Transaction> transactions = (keyword == null) ? (List<Transaction>) transactionRepository.findAll() : transactionRepository.findAllByPartnerNameContains(keyword);
        List<Transaction> list;

        if (transactions.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, transactions.size());
            list = transactions.subList(startItem, toIndex);
        }

        Page<Transaction> transactionPage
                = new PageImpl<Transaction>(list, PageRequest.of(currentPage, pageSize), transactions.size());

        return transactionPage;
    }
}
