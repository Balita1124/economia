package com.balita.economia.controller;

import com.balita.economia.model.Partner;
import com.balita.economia.model.Transaction;
import com.balita.economia.playload.PartnerForm;
import com.balita.economia.playload.SearchForm;
import com.balita.economia.playload.TransactionForm;
import com.balita.economia.service.AccountService;
import com.balita.economia.service.PartnerService;
import com.balita.economia.service.TransactionService;
import com.balita.economia.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/transactions")
public class TransactionController{

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @Autowired
    PartnerService partnerService;

    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }


    @ModelAttribute("transactionForm")
    public TransactionForm transactionForm() {
        return new TransactionForm();
    }

    @GetMapping
    public String index(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            @ModelAttribute("searchForm") Optional<SearchForm> searchForm,
            Model model) {
        String keyword = (searchForm.isPresent() && searchForm.get().getKeyword() != null) ? searchForm.get().getKeyword().trim() : null;
        Page<Transaction> transactions = transactionService.transactionPagedList(PageRequest.of(page - 1, size), keyword);

        int totalPages = transactions.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("transactions", transactions);
        return "transactions/index";
    }

    @GetMapping("/add")
    public String addTransaction(Model model) {
        model.addAttribute("transactionAccounts", accountService.findAccounts());
        model.addAttribute("transactionPartners", partnerService.findPartners());
        return "transactions/add";
    }
}
