package kz.aigulsharip.springboot.security.repository;

import kz.aigulsharip.springboot.security.model.GrantAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantAccessRepository extends JpaRepository<GrantAccess, Long> {

    GrantAccess findByAccessValue(String accessValue);



}
