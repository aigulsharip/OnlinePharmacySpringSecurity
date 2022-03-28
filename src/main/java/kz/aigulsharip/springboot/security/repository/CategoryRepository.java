package kz.aigulsharip.springboot.security.repository;

import kz.aigulsharip.springboot.security.model.Category;
import kz.aigulsharip.springboot.security.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    public List<Category> findAllByOrderByNameAsc();





}
