package andrey.dev.backendforcursach.security;

import andrey.dev.backendforcursach.enums.Role;
import andrey.dev.backendforcursach.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(User user) implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole().toString();

        if (role == null || role.isBlank()) {
            return List.of(new SimpleGrantedAuthority(Role.USER.name()));
        }

        Role userRole = Role.fromString(role);
        return List.of(new SimpleGrantedAuthority(userRole.name()));
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getLogin();
    }
}
