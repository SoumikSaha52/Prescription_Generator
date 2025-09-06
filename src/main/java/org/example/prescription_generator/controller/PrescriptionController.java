package org.example.prescription_generator.controller;

import jakarta.validation.Valid;
import org.example.prescription_generator.entity.Prescription;
import org.example.prescription_generator.services.PrescriptionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService service;

    public PrescriptionController(PrescriptionService service) {
        this.service = service;
    }

    @GetMapping
    public String listPrescriptions(Model model) {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now();
        model.addAttribute("prescriptions", service.findByDateRange(start, end));
        return "prescriptions";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        return "prescription_form";
    }

    @PostMapping
    public String save(@Valid @ModelAttribute Prescription prescription, BindingResult result) {
        if (result.hasErrors()) return "prescription_form";
        service.save(prescription);
        return "redirect:/prescriptions";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("prescription", service.findById(id));
        return "prescription_form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        service.delete(id);
        return "redirect:/prescriptions";
    }
}
