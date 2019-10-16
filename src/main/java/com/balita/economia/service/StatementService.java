package com.balita.economia.service;

import com.balita.economia.model.Statement;
import com.balita.economia.model.Transaction;
import com.balita.economia.repository.StatementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StatementService {
    @Autowired
    StatementRepository statementRepository;

    public Page<Statement> statementPagedList(PageRequest pageable, String keyword) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Statement> statements = (keyword == null) ? (List<Statement>) statementRepository.findAll() : statementRepository.findAllByNameContains(keyword);
        List<Statement> list;

        if (statements.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, statements.size());
            list = statements.subList(startItem, toIndex);
        }

        Page<Statement> statementsPage
                = new PageImpl<Statement>(list, PageRequest.of(currentPage, pageSize), statements.size());
        return statementsPage;
    }

    public void createStatement(Transaction transSaved) {
        Statement statement = new Statement(
                transSaved.getPartnerName() + " - " + transSaved.getTransTypeEnum(),
                transSaved.getAccount(),
                transSaved,
                transSaved.getAccount().getAmount(),
                transSaved.getDate()
        );
        statementRepository.save(statement);
    }
}
