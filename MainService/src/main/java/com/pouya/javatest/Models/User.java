package com.pouya.javatest.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_password")
    @JsonIgnore
    private String password;

    @CreationTimestamp
    @Column(name = "user_created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "user" , fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<UserRolesMap> roles;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<UserRolesMap> getRoles() {
        return roles;
    }

    public void setRoles(List<UserRolesMap> roles) {
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() { return true; }
    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() { return true; }
    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() { return true; }
    @Override
    @JsonIgnore
    public boolean isEnabled() { return true; }


}
