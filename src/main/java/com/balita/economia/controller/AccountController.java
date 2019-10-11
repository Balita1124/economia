package com.balita.economia.controller;

import com.balita.economia.model.Account;
import com.balita.economia.model.Partner;
import com.balita.economia.model.User;
import com.balita.economia.playload.AccountForm;
import com.balita.economia.playload.PartnerForm;
import com.balita.economia.security.CurrentUser;
import com.balita.economia.security.UserPrincipal;
import com.balita.economia.service.AccountService;
import com.balita.economia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @ModelAttribute("accountForm")
    public AccountForm accountForm() {
        return new AccountForm();
    }

    @GetMapping("/add")
    public String addAccount(Model model) {
        return "accounts/add";
    }

    @PostMapping("/create")
    public String createAccount(@ModelAttribute("accountForm") @Valid AccountForm accountForm, BindingResult result, Model model, @CurrentUser UserPrincipal currentUser) {
        if (result.hasErrors()) {
            return "accounts/add";
        }
        User user = userService.findUserById(currentUser.getId());
        if(user == null){
            return "redirect:/logout";
        }
        Account account = new Account();
        account = accountForm.buildAccount(account);
        account.setUser(user);
        accountService.saveAccount(account);
        return "redirect:/profil?success";
    }

    @GetMapping("/edit/{id}")
    public String editAccount(@PathVariable("id") Long accountId, Model model) {
        Account account = accountService.findAccountById(accountId);
        if (account == null) {
            return "commons/404";
        }
        model.addAttribute("account", account);
        return "accounts/edit";
    }

    @PostMapping("/update/{id}")
    public String createAccount(
            @PathVariable("id") Long accountId,
            @ModelAttribute("accountForm") @Valid AccountForm accountForm,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "accounts/edit";
        }
        Account oldAccount = accountService.findAccountById(accountId);
        if (oldAccount == null) {
            return "commons/404";
        }
        oldAccount = accountForm.buildAccount(oldAccount);
        accountService.saveAccount(oldAccount);
        return "redirect:/profil?success";
    }

    @GetMapping("/delete/{id}")
    public String deleteAccount(@PathVariable("id") Long accountId, Model model) {
        Account account = accountService.findAccountById(accountId);
        if (account == null) {
            return "commons/404";
        }
        accountService.deleteAccount(account);
        return "redirect:/profil?success";
    }
}
