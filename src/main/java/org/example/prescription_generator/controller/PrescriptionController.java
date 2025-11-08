package org.example.prescription_generator.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.example.prescription_generator.entity.Prescription;
import org.example.prescription_generator.repository.PrescriptionRepository;
import org.example.prescription_generator.services.PrescriptionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionRepository prescriptionRepository;
    private final PrescriptionService service;

    public PrescriptionController(PrescriptionRepository prescriptionRepository, PrescriptionService service) {
        this.prescriptionRepository = prescriptionRepository;
        this.service = service;
    }

    @GetMapping
    public String listPrescriptions(Model model) {
        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now();
        model.addAttribute("prescriptions", service.findByDateRange(start, end));
        return "prescription-list"; // 👈 সঠিক HTML টেমপ্লেট
    }

    @PostMapping("/filter")
    public String filterPrescriptions(
            @RequestParam("start") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam("end") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            Model model) {

        model.addAttribute("prescriptions", service.findByDateRange(start, end));
        return "prescription-list";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("prescription", new Prescription());
        return "prescription_form";
    }

    @PostMapping
    @Transactional
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
