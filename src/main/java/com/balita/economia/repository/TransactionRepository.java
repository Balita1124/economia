package com.balita.economia.repository;

import com.balita.economia.model.Transaction;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {
    List<Transaction> findAllByPartnerNameContains(String partnerName);
}
