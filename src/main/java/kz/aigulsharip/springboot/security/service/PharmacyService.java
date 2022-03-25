package kz.aigulsharip.springboot.security.service;

import kz.aigulsharip.springboot.security.model.Category;
import kz.aigulsharip.springboot.security.model.Country;
import kz.aigulsharip.springboot.security.model.Medication;
import kz.aigulsharip.springboot.security.repository.CategoryRepository;
import kz.aigulsharip.springboot.security.repository.CountryRepository;
import kz.aigulsharip.springboot.security.repository.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PharmacyService {

    private final MedicationRepository medicationRepository;

    private final CountryRepository countryRepository;

    private final CategoryRepository categoryRepository;


    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    public Medication addMedication(Medication medication) {
        return medicationRepository.save(medication);
    }


    public Medication getMedication(Long id) {
        return medicationRepository.findById(id).orElse(null);
    }

    public Medication saveMedication(Medication medication) {
        return medicationRepository.save(medication);
    }

    public void deleteMedication(Long id) {
        medicationRepository.deleteById(id);
    }


    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountry(Long id) {
        return  countryRepository.findById(id).orElse(null);
    }
    public List<Category> getAllCategories () {
        return categoryRepository.findAll();
    }

    public Category getCategory (Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getCategoriesByMedication (Medication medication) {
        List<Category> itemCategories = medication.getCategories();
        List<Category> possibleCategories = categoryRepository.findAll();

        possibleCategories.removeAll(itemCategories);

        return possibleCategories;
    }

    public List<Medication> getMedicationByName(String name) {
        return medicationRepository.findMedicationByName(name);
    }




}
