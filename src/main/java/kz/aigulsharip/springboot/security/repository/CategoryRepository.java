package kz.aigulsharip.springboot.security.repository;

import kz.aigulsharip.springboot.security.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {



}
