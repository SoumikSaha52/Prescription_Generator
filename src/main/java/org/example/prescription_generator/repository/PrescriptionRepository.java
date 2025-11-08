package org.example.prescription_generator.repository;

import org.example.prescription_generator.entity.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    List<Prescription> findByPrescriptionDateBetween(LocalDate start, LocalDate end);

}
