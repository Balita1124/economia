package com.balita.economia.controller;

import com.balita.economia.model.Statement;
import com.balita.economia.model.Transaction;
import com.balita.economia.playload.SearchForm;
import com.balita.economia.service.StatementService;
import com.balita.economia.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/statements")
public class StatementController {

    @Autowired
    StatementService statementService;

    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }


    @GetMapping
    public String index(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @ModelAttribute("searchForm") Optional<SearchForm> searchForm,
            Model model) {
        String keyword = (searchForm.isPresent() && searchForm.get().getKeyword() != null) ? searchForm.get().getKeyword().trim() : null;
        Page<Statement> statements = statementService.statementPagedList(PageRequest.of(page - 1, size), keyword);

        int totalPages = statements.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("statements", statements);
        return "statements/index";
    }
}
