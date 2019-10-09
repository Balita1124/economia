package com.balita.economia.repository;

import com.balita.economia.model.Partner;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository  extends PagingAndSortingRepository<Partner, Long> {
//    List<Partner> findAll(Pageable pageable);
}
