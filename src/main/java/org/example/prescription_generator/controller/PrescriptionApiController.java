package org.example.prescription_generator.controller;

import org.example.prescription_generator.entity.Prescription;
import org.example.prescription_generator.services.PrescriptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/prescriptions")
public class PrescriptionApiController {


    private final PrescriptionService service;

    public PrescriptionApiController(PrescriptionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Prescription> getAll() {
        return service.findAll();
    }
}
