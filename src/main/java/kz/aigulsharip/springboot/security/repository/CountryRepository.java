package kz.aigulsharip.springboot.security.repository;


import kz.aigulsharip.springboot.security.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
