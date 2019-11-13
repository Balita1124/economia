package com.balita.economia.controller;

import com.balita.economia.model.*;
import com.balita.economia.playload.CreditForm;
import com.balita.economia.playload.CreditPayForm;
import com.balita.economia.playload.SearchForm;
import com.balita.economia.playload.TransactionForm;
import com.balita.economia.service.*;
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

import javax.jws.WebParam;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/credits")
public class CreditController {

    @Autowired
    CreditService creditService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    StatementService statementService;

    @Autowired
    AccountService accountService;

    @Autowired
    PartnerService partnerService;

    @ModelAttribute("searchForm")
    public SearchForm searchForm() {
        return new SearchForm();
    }

    @ModelAttribute("creditForm")
    public CreditForm creditForm() {
        CreditForm form = new CreditForm();
//        form.setDate(new Date());
        return new CreditForm();
    }

    @ModelAttribute("creditPayForm")
    public CreditPayForm creditPayForm() {
        return new CreditPayForm();
    }

    @ModelAttribute("creditAccounts")
    public List<Account> creditAccounts() {
        return accountService.findAccounts();
    }

    @ModelAttribute("creditPartners")
    public List<Partner> creditPartners() {
        return partnerService.findPartners();
    }

    @ModelAttribute("creditTypes")
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
        Page<Credit> credits = creditService.creditPagedList(PageRequest.of(page - 1, size), keyword);

        int totalPages = credits.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("credits", credits);
        return "credits/index";
    }

    @GetMapping("/add")
    public String addCredit(Model model) {
        return "credits/add";
    }

    @PostMapping("/create")
    public String createPartner(@ModelAttribute("creditForm") @Valid CreditForm creditForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "credits/add";
        }
        Account accountConcerned = new Account();
        if (creditForm.getTransTypeEnum() == TransTypeEnum.INCOME) {
            accountConcerned = accountService.makePayment(creditForm.getAccount(), creditForm.getAmount());
        } else {
            if (creditForm.getAccount().getAmount() < creditForm.getAmount()) {
                result.rejectValue("amount", null, "Not enougth money");
                return "credits/add";
            }
            accountConcerned = accountService.makeWithdrawal(creditForm.getAccount(), creditForm.getAmount());
        }
        Transaction newTrans = new Transaction(
                creditForm.getPartner(),
                accountConcerned,
                creditForm.getAmount(),
                creditForm.getDate(),
                creditForm.getRemarks(),
                creditForm.getTransTypeEnum()
        );
        Transaction transSaved = transactionService.saveTransaction(newTrans);
        Credit newCredit = new Credit(
                creditForm.getPartner(),
                creditForm.getAccount(),
                creditForm.getTransTypeEnum(),
                creditForm.getAmount(),
                creditForm.getDate(),
                creditForm.getRemarks()
        );
        newCredit.setTransaction(transSaved);
        newCredit.setAmountLeft(creditForm.getAmount());
        newCredit.setState(CreditStateEnum.NEW);
        statementService.createStatement(transSaved);
        Credit creditSaved = creditService.saveCredit(newCredit);
        return "redirect:/credits?success";
    }

    @GetMapping("/pay/{id}")
    public String payCredit(@PathVariable("id") Long creditId, Model model) {
//        Partner partner = partnerService.findPartnerById(partnerId);
        Credit credit = creditService.findCreditById(creditId);
        if (credit == null) {
            return "commons/404";
        }
        CreditPayForm creditPayForm = new CreditPayForm();
        creditPayForm.setDue(credit.getAmount());
        creditPayForm.setLeft(credit.getAmountLeft());
        creditPayForm.setAccount(credit.getAccount());
        model.addAttribute("creditPayForm", creditPayForm);
        model.addAttribute("credit", credit);
        return "credits/edit";
    }

    @PostMapping("/pay/{id}")
    public String processPay(@ModelAttribute("creditPayForm") @Valid CreditPayForm creditPayForm, @PathVariable("id") Long creditId, Model model, BindingResult result) {
        Credit credit = creditService.findCreditById(creditId);
        if (credit == null) {
            return "commons/404";
        }
        if (result.hasErrors()) {
            return "credits/edit";
        }
        if(credit.getTransTypeEnum() == TransTypeEnum.INCOME){
            
        }else if(credit.getTransTypeEnum() == TransTypeEnum.OUTGOING){

        }
        return "redirect:/credits?success";
    }
}
