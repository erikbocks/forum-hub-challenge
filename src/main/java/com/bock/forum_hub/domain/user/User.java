package com.bock.forum_hub.domain.user;

import com.bock.forum_hub.domain.user.dtos.UserRegisterDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "User")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(UserRegisterDTO data, String encodedPassword) {
        this.name = data.name();
        this.email = data.email();
        this.password = encodedPassword;
    }

    @PrePersist
    void updateRole() {
        if (this.role == null) {
            this.role = UserRole.COMMON;
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        switch (this.role) {
            case ADMIN -> {
                return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_MODERATOR"), new SimpleGrantedAuthority("ROLE_HELPER"), new SimpleGrantedAuthority("ROLE_COMMON"));
            }
            case MODERATOR -> {
                return List.of(new SimpleGrantedAuthority("ROLE_MODERATOR"), new SimpleGrantedAuthority("ROLE_HELPER"), new SimpleGrantedAuthority("ROLE_COMMON"));
            }
            case HELPER -> {
                return List.of(new SimpleGrantedAuthority("ROLE_HELPER"), new SimpleGrantedAuthority("ROLE_COMMON"));
            }
            default -> {
                return List.of(new SimpleGrantedAuthority("ROLE_COMMON"));
            }
        }
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
