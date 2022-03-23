package kz.aigulsharip.springboot.security.service;

import kz.aigulsharip.springboot.security.model.AuthUser;
import kz.aigulsharip.springboot.security.model.GrantAccess;
import kz.aigulsharip.springboot.security.repository.AuthUserRepository;
import kz.aigulsharip.springboot.security.repository.GrantAccessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final GrantAccessRepository grantAccessRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = authUserRepository.findAuthUserByEmail(username);

        if (user != null) return user;

        throw new UsernameNotFoundException("User not found");
    }

    public AuthUser createUser (AuthUser authUser) {

        GrantAccess userAccess = grantAccessRepository.findByAccessValue("ROLE_USER");
        List<GrantAccess> grantAccessList = new ArrayList<>();
        grantAccessList.add(userAccess);
        authUser.setGrantAccess(grantAccessList);
        return authUserRepository.save(authUser);
    }


    public AuthUser getUserByEmail (String email) {
        return authUserRepository.findAuthUserByEmail(email);
    }

    public AuthUser updateUser (AuthUser user ) {
        return authUserRepository.save(user);
    }
}
