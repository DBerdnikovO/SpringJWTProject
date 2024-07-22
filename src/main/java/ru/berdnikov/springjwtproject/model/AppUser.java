package ru.berdnikov.springjwtproject.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author danilaberdnikov on AppUser.
 * @project SpringJWTProject
 */
@Getter
@Setter
public class AppUser extends User {
    private String userId;

    public AppUser(String username, String userId, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.userId = userId;
    }

    public AppUser(UserModel userModel, Collection<? extends GrantedAuthority> authorities) {
        super(userModel.getUsername(), userModel.getPassword().toString(), authorities);
        this.userId = userId;
    }
}
