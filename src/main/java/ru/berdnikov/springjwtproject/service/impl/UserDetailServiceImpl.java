package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.repository.UserRepository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on UserDetailServiceImpl.
 * @project SpringJWTProject
 */
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserModel> userOptional = userRepository.findUserModelByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("UserModel not found");
        }

        UserModel userModel = userOptional.get();
        Set<GrantedAuthority> authorities = userModel.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toSet());

        return new AppUser(userModel, authorities);
    }
}
