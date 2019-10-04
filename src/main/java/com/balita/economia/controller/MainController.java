package com.balita.economia.controller;

import com.balita.economia.model.Person;
import com.balita.economia.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class MainController {

    private final PersonService personService;

    @Autowired
    public MainController(PersonService personService) {
        this.personService = personService;
    }

    @RequestMapping("/")
    public String index(Model model){
        List<Person> personList = personService.getPersons();
        return "main/index";
    }
}
