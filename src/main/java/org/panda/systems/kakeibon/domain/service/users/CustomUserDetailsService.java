package org.panda.systems.kakeibon.domain.service.users;

import jakarta.transaction.Transactional;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.repository.users.UsersDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UsersDetailRepository usersDetailRepository;

    public CustomUserDetailsService() {
        this.usersDetailRepository = null;
    }

    @Autowired
    public CustomUserDetailsService(UsersDetailRepository usersDetailRepository) {
        this.usersDetailRepository = usersDetailRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        if (this.usersDetailRepository == null) {

        }
        Users users = Objects.requireNonNull(this.usersDetailRepository).findByUsername(username);
        if (users == null) {
            //noinspection ThrowableNotThrown
            new UsernameNotFoundException(username + " is not found.");
        }

        return new CustomUserDetails(users);
    }

    @Transactional
    public List<CustomUserDetails> findAllUsersToForm() {
        List<Users> users = Objects.requireNonNull(usersDetailRepository).findAll(
                Sort.by(Sort.Direction.DESC,
                        "UserId")
        );
        List<CustomUserDetails> customUserDetails = new ArrayList<>();
        for (Users user : users) {
            customUserDetails.add(new CustomUserDetails(user));
        }
        return customUserDetails;
    }

    public Users findByUserId(Long userId) {
        return Objects.requireNonNull(usersDetailRepository).findByUserId(userId);
    }

    public CustomUserDetails findByUsername(String username) {
        Users users = Objects.requireNonNull(usersDetailRepository).findByUsername(username);
        return new CustomUserDetails(users);

    }

    public boolean existsByUserId(Long userId) {

        return Objects.requireNonNull(usersDetailRepository).existsByUserId(userId);
    }

    public boolean existsByUsername(String username) {

        return Objects.requireNonNull(usersDetailRepository).existsByUsername(username);
    }

    public Users saveAndFlush(Users entity) throws Exception {
        try {
            return Objects.requireNonNull(this.usersDetailRepository).saveAndFlush(entity);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
