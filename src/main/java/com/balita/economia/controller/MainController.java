package com.balita.economia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(Model model){
        return "main/index";
    }

    @RequestMapping("/about")
    public String test(Model model){
        return "main/about";
    }
}
