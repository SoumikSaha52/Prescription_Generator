package org.example.prescription_generator.services;

import jakarta.transaction.Transactional;
import org.example.prescription_generator.entity.Prescription;
import org.example.prescription_generator.repository.PrescriptionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrescriptionService {


    private final PrescriptionRepository repo;

    public PrescriptionService(PrescriptionRepository repo) {
        this.repo = repo;
    }

    public List<Prescription> findByDateRange(LocalDate start, LocalDate end) {
        return repo.findByPrescriptionDateBetween(start, end);
    }

    public List<Prescription> findAll() { return repo.findAll(); }
    @Transactional
    public Prescription save(Prescription p) { return repo.save(p); }

    public Prescription findById(Integer id) { return repo.findById(id).orElseThrow(); }

    public void delete(Integer id) { repo.deleteById(id); }
}
