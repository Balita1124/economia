package com.balita.economia.repository;

import com.balita.economia.model.Credit;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CreditRepository extends PagingAndSortingRepository<Credit, Long> {
    List<Credit> findAllByPartnerNameContains(String keyword);
}
