package com.balita.economia.controller;

import com.balita.economia.model.Partner;
import com.balita.economia.playload.PartnerForm;
import com.balita.economia.service.PartnerService;
import com.balita.economia.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/partners")
public class PartnerController {

    @Autowired
    PartnerService partnerService;

    @ModelAttribute("partnerForm")
    public PartnerForm partnerForm() {
        return new PartnerForm();
    }

    @GetMapping
    public String index(
            @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size,
            Model model) {
        model.addAttribute("partners", partnerService.partnerList(page, size));
        return "partners/index";
    }

    @GetMapping("/add")
    public String addPartner(Model model) {
        return "partners/add";
    }

    @PostMapping("/create")
    public String createPartner(@ModelAttribute("partnerForm") @Valid PartnerForm partnerForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "partners/add";
        }
        Partner newPartner = new Partner(
                partnerForm.getFirstname(),
                partnerForm.getLastname(),
                partnerForm.getPhone(),
                partnerForm.getEmail(),
                partnerForm.getAccount()
        );

        Partner partnerSaved = partnerService.createPartner(newPartner);
        return "redirect:/partners?success";
    }

}
