package com.hsba.bi.fitnessstudio.Fitnessstudio.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Service-Klasse für User-Adapter
 */

@Service
@Transactional
@RequiredArgsConstructor
public class UserAdapterService implements UserDetailsService {

    private final UserRepository repository;

    //Methode, die UserDetails Objekt beim Einloggen zurückgibt
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findDistinctByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserAdapter(user);
    }

}
