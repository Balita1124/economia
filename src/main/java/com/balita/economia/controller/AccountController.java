package com.balita.economia.controller;

import com.balita.economia.model.User;
import com.balita.economia.playload.AccountForm;
import com.balita.economia.security.CurrentUser;
import com.balita.economia.security.UserPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/accounts")
public class AccountController {

    @ModelAttribute("accountForm")
    AccountForm accountForm(@CurrentUser User currentUser) {
        AccountForm form = new AccountForm();
        form.setUser(currentUser);
        return form;
    }

    @GetMapping("/add")
    public String addAccount(Model model) {
        return "accounts/add";
    }
}
