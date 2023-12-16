package com.mj.kimsnote.entity.member;

import com.mj.kimsnote.entity.member.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
public class Member implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;

    private String name;

    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private Set<Role> roles;

    @Builder
    public Member(Long id, String email, String password, String name, String phone, Set<Role> roles) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.roles = roles;
    }

    public Member() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of(new SimpleGrantedAuthority("user"));
        return this.roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getValue()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword(){
        return password;
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
