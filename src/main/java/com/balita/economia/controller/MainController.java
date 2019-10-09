package com.balita.economia.controller;

import com.balita.economia.model.User;
import com.balita.economia.security.CurrentUser;
import com.balita.economia.security.UserPrincipal;
import com.balita.economia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index(Model model) {
        return "main/index";
    }

    @RequestMapping("/about")
    public String test(Model model) {
        return "main/about";
    }

    @RequestMapping("/profil")
    public String profilUser(Model model, @CurrentUser UserPrincipal currentUser) {
        System.out.println("currentUser: " + currentUser);
        User user = userService.findUserById(currentUser.getId());
        if (user == null) {
            return "redirect:/logout";
        }
        model.addAttribute("user", user);
        return "main/profil";
    }

}
