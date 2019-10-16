package com.balita.economia.controller;

import com.balita.economia.model.*;
import com.balita.economia.playload.PartnerForm;
import com.balita.economia.playload.SearchForm;
import com.balita.economia.playload.TransactionForm;
import com.balita.economia.service.AccountService;
import com.balita.economia.service.PartnerService;
import com.balita.economia.service.StatementService;
import com.balita.economia.service.TransactionService;
import com.balita.economia.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @Autowired
    PartnerService partnerService;

    @Autowired
    StatementService statementService;

    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }


    @ModelAttribute("transactionForm")
    public TransactionForm transactionForm() {
        TransactionForm form = new TransactionForm();
        form.setDate(new Date());
        return new TransactionForm();
    }

    @ModelAttribute("transactionAccounts")
    public List<Account> transactionAccounts() {
        return accountService.findAccounts();
    }

    @ModelAttribute("transactionPartners")
    public List<Partner> transactionPartners() {
        return partnerService.findPartners();
    }

    @ModelAttribute("transTypes")
    public List<TransTypeEnum> transTypes() {
        return Arrays.asList(TransTypeEnum.INCOME, TransTypeEnum.OUTGOING);
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
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
        return "transactions/add";
    }

    @PostMapping("/create")
    public String createPartner(@ModelAttribute("transactionForm") @Valid TransactionForm transactionForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "transactions/add";
        }
        Account accountConcerned = new Account();
        if (transactionForm.getTransTypeEnum() == TransTypeEnum.INCOME) {
            accountConcerned = accountService.makePayment(transactionForm.getAccount(), transactionForm.getAmount());
        } else {
            if (transactionForm.getAccount().getAmount() < transactionForm.getAmount()) {
                result.rejectValue("amount", null, "Not enougth money");
                return "transactions/add";
            }
            accountConcerned = accountService.makeWithdrawal(transactionForm.getAccount(), transactionForm.getAmount());
        }
        Transaction newTrans = new Transaction(
                transactionForm.getPartner(),
                accountConcerned,
                transactionForm.getAmount(),
                transactionForm.getDate(),
                transactionForm.getRemarks(),
                transactionForm.getTransTypeEnum()
        );
        Transaction transSaved = transactionService.saveTransaction(newTrans);
        statementService.createStatement(transSaved);
        return "redirect:/transactions?success";
    }
}
