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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        account.setName(accountForm.getName());
        account.setNumber(accountForm.getNumber());
        account.setDescription(accountForm.getDescription());
        account.setAmount(accountForm.getAmount());
        account.setUser(user);
        accountService.saveAccount(account);
        return "redirect:/profil?success";
    }
}
