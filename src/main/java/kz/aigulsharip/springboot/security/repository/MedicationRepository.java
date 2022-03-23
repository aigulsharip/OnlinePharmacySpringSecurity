package kz.aigulsharip.springboot.security.repository;


import kz.aigulsharip.springboot.security.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {


}
