package fr.limyrac.poubelle.security;

import fr.limyrac.poubelle.model.Utilisateur;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class UserSpringSecurity implements UserDetails {
    private final Utilisateur utilisateur;
    public UserSpringSecurity(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(utilisateur.getRole());
    }

    @Override
    public String getPassword() {
        return utilisateur.getPassword();
    }

    @Override
    public String getUsername() {
        return utilisateur.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return utilisateur.getActif();
    }

    @Override
    public boolean isAccountNonLocked() {
        return utilisateur.getActif();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return utilisateur.getActif();
    }

    @Override
    public boolean isEnabled() {
        return utilisateur.getActif();
    }
}
