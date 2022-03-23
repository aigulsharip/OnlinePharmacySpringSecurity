package kz.aigulsharip.springboot.security.repository;

import kz.aigulsharip.springboot.security.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Long> {

    AuthUser findAuthUserByEmail(String email);
}
