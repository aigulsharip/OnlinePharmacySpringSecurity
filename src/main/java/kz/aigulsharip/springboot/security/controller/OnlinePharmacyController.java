package kz.aigulsharip.springboot.security.controller;

import kz.aigulsharip.springboot.security.model.Category;
import kz.aigulsharip.springboot.security.model.Country;
import kz.aigulsharip.springboot.security.model.Medication;
import kz.aigulsharip.springboot.security.repository.MedicationRepository;
import kz.aigulsharip.springboot.security.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/medications")
public class OnlinePharmacyController extends BaseController {
    private final PharmacyService pharmacyService;
    private final MedicationRepository medicationRepository;

    @GetMapping(value = "/")
    public String getAllMedications(Model model) {
        List<Medication> medications = pharmacyService.getAllMedications();
        model.addAttribute("medications", medications);
        model.addAttribute("currentUser", getCurrentUser());

        return "medications";
    }

    @GetMapping(value = "/addMedication")
    public String addMedicationPage(Model model) {
        List<Country> countries = pharmacyService.getAllCountries();
        model.addAttribute("countries", countries);
        List<Category> categories = pharmacyService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("currentUser", getCurrentUser());


        return "addMedication";
    }

    @PostMapping(value = "/addMedication")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")

    public String addMedication(@RequestParam(name = "name") String name,
                                @RequestParam(name = "dosage") String dosage,
                                @RequestParam(name = "form") String form,
                                @RequestParam(name = "price") int price,
                                @RequestParam(name = "quantity") int quantity,
                                @RequestParam(name = "country_id") Long countryId,
                                @RequestParam(name = "availability") String availability,
                                @RequestParam(name = "image") String image) {
        Country cnt = pharmacyService.getCountry(countryId);
        if (cnt != null) {
            Medication medication = new Medication();
            medication.setName(name);
            medication.setDosage(dosage);
            medication.setForm(form);
            medication.setPrice(price);
            medication.setQuantity(quantity);
            medication.setCountry(cnt);
            medication.setAvailability(availability);
            medication.setImage(image);
            pharmacyService.addMedication(medication);
        }
        return "redirect:/";

    }

    @GetMapping(value = "/details/edit/{id}.html")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String readMedicationForAdmin(Model model, @PathVariable(name = "id") Long id) {
        Medication medication = pharmacyService.getMedication(id);
        model.addAttribute("medication", medication);
        List<Country> countries = pharmacyService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("currentUser", getCurrentUser());

        if (medication != null) {
            List<Category> categories = pharmacyService.getCategoriesByMedication(medication);
            model.addAttribute("categories", categories);

        }
        return "medicationDetailsForAdmin";
    }



    @GetMapping(value = "/details/{id}.html")
    public String readMedicationForUsers(Model model, @PathVariable(name = "id") Long id) {
        Medication medication = pharmacyService.getMedication(id);
        model.addAttribute("medication", medication);
        List<Country> countries = pharmacyService.getAllCountries();
        model.addAttribute("countries", countries);
        model.addAttribute("currentUser", getCurrentUser());

        if (medication != null) {
            List<Category> categories = pharmacyService.getCategoriesByMedication(medication);
            model.addAttribute("categories", categories);

        }


        return "medicationDetailsForUsers";
    }

    @PostMapping(value = "/saveMedication")
    public String saveMedication(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "dosage") String dosage,
                                 @RequestParam(name = "form") String form,
                                 @RequestParam(name = "price") int price,
                                 @RequestParam(name = "quantity") int quantity,
                                 @RequestParam(name = "country_id") Long countryId) {

        Medication medication = pharmacyService.getMedication(id);
        Country country = pharmacyService.getCountry(countryId);
        if (medication != null && country != null) {

            medication.setName(name);
            medication.setDosage(dosage);
            medication.setForm(form);
            medication.setPrice(price);
            medication.setQuantity(quantity);
            medication.setCountry(country);
            pharmacyService.saveMedication(medication);
        }

        return "redirect:/";

    }

    @PostMapping(value = "/deleteMedication")
    public String deleteMedication(@RequestParam(name = "id") Long id) {

        Medication medication = pharmacyService.getMedication(id);

        if (medication != null) {
            //pharmacyService.deleteMedication(id);
            medicationRepository.deleteById(id);
        }
        return "redirect:/";

    }

    @PostMapping(value = "/assign-category")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")

    public String assignCategory(@RequestParam(name = "id") Long id,
                                 @RequestParam(name = "medication_id") Long medicationId) {
        Medication medication = pharmacyService.getMedication(medicationId);
        Category category = pharmacyService.getCategory(id);

        if (medication != null && category != null) {
            List<Category> categories = medication.getCategories();
            if (categories == null) {
                categories = new ArrayList<>();
            }
            categories.add(category);
            medication.setCategories(categories);
            pharmacyService.saveMedication(medication);
            return "redirect:/medications/details/edit/" + medicationId + ".html";
        }
        return "redirect:/";
    }

    @PostMapping(value = "/unassign-category")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public String unAssignCategory(@RequestParam(name = "id") Long id,
                                   @RequestParam(name = "medication_id") Long medicationId) {
        Medication medication = pharmacyService.getMedication(medicationId);
        Category category = pharmacyService.getCategory(id);

        if (medication != null && category != null) {
            List<Category> categories = medication.getCategories();
            if (categories == null) {
                categories = new ArrayList<>();
            }
            categories.remove(category);
            medication.setCategories(categories);
            pharmacyService.saveMedication(medication);
            return "redirect:/medications/details/edit/" + medicationId + ".html";
        }
        return "redirect:/";


    }


    @PostMapping(value = "/searchMedication")
    public String searchMedication(@RequestParam(name = "name") String name, Model model) {
        List<Medication> foundMedications = pharmacyService.getMedicationByName(name);
        model.addAttribute("medications", foundMedications);
        model.addAttribute("currentUser", getCurrentUser());

        return "foundMedications";

    }



}
