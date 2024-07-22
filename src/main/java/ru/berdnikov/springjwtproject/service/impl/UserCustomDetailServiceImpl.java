package ru.berdnikov.springjwtproject.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.berdnikov.springjwtproject.model.AppUser;
import ru.berdnikov.springjwtproject.model.UserModel;
import ru.berdnikov.springjwtproject.repository.UserRepository;
import ru.berdnikov.springjwtproject.service.UserCustomDetailsService;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author danilaberdnikov on UserCustomDetailServiceImpl.
 * @project SpringJWTProject
 */
@Service
@RequiredArgsConstructor
public class UserCustomDetailServiceImpl implements UserCustomDetailsService {
    private final UserRepository userRepository;

    @Override
    public AppUser loadUserByUsername(String email) {
        Optional<UserModel> userOptional = userRepository.findUserModelByEmail(email);
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
