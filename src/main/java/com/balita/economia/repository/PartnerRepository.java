package com.balita.economia.repository;

import com.balita.economia.model.Partner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartnerRepository extends PagingAndSortingRepository<Partner, Long> {

    List<Partner> findAllByFirstnameContainsOrLastnameContains(String firstname, String lastname);
}
