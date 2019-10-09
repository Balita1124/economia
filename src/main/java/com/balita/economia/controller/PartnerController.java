package com.balita.economia.controller;

import com.balita.economia.model.Partner;
import com.balita.economia.playload.PartnerForm;
import com.balita.economia.service.PartnerService;
import com.balita.economia.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        Page<Partner> partners = partnerService.partnerPagedList(PageRequest.of(page - 1, size));
        int totalPages = partners.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("partners", partners);
        return "partners/index";
    }

    @GetMapping("/list")
    public String list(
            @SortDefault("firstname") Pageable pageable,
            Model model) {
        Page<Partner> partners = partnerService.partnerPaged(pageable);
        model.addAttribute("partners", partners);
        return "partners/list";
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

        Partner partnerSaved = partnerService.savePartner(newPartner);
        return "redirect:/partners?success";
    }

    @GetMapping("/show/{id}")
    public String showPartner(@PathVariable("id") Long partnerId, Model model) {
        Partner partner = partnerService.findPartnerById(partnerId);
        if (partner == null) {
            return "commons/404";
        }
        model.addAttribute("partner", partner);
        return "partners/show";
    }

    @GetMapping("/edit/{id}")
    public String editPartner(@PathVariable("id") Long partnerId, Model model) {
        Partner partner = partnerService.findPartnerById(partnerId);
        if (partner == null) {
            return "commons/404";
        }
        model.addAttribute("partner", partner);
        return "partners/edit";
    }

    @PostMapping("/update/{id}")
    public String createPartner(
            @PathVariable("id") Long partnerId,
            @ModelAttribute("partnerForm") @Valid PartnerForm partnerForm,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "partners/edit";
        }
        Partner oldPartner = partnerService.findPartnerById(partnerId);
        if (oldPartner == null) {
            return "commons/404";
        }
        oldPartner.setFirstname(partnerForm.getFirstname());
        oldPartner.setLastname(partnerForm.getLastname());
        oldPartner.setPhone(partnerForm.getPhone());
        oldPartner.setEmail(partnerForm.getEmail());
        oldPartner.setAccount(partnerForm.getAccount());

        Partner partnerSaved = partnerService.savePartner(oldPartner);
        return "redirect:/partners?success";
    }

    @GetMapping("/delete/{id}")
    public String deletePartner(@PathVariable("id") Long partnerId, Model model) {
        Partner partner = partnerService.findPartnerById(partnerId);
        if (partner == null) {
            return "commons/404";
        }
        partnerService.deletePartner(partner);
        return "redirect:/partners?success";
    }

}
