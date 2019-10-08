package com.balita.economia.repository;

import com.balita.economia.model.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;

@Repository
public interface PartnerRepository  extends PagingAndSortingRepository<Partner, Long> {
//    List<Partner> findAll(Pageable pageable);
}
