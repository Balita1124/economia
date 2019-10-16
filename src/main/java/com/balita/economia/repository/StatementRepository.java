package com.balita.economia.repository;

import com.balita.economia.model.Statement;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StatementRepository extends PagingAndSortingRepository<Statement, Long> {
    List<Statement> findAllByNameContains(String keyword);
}
