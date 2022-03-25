package kz.aigulsharip.springboot.security.repository;


import kz.aigulsharip.springboot.security.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    List<Medication> findMedicationByName(String name);

    /*
    List<Item> findAllByPriceGreaterThan(int price);

    @Query("SELECT it FROM Item it WHERE it.price >= :price")
    List<Item> getTopItems(@Param("price") int price);

     */





}
