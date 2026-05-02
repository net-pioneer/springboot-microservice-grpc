package com.pouya.javatest.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.pouya.library.Enums.UserRoles;
import jakarta.persistence.*;

@Entity
@Table(name = "user_roles_map")
public class UserRolesMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "urm_id")
    private Long id;

    @Column(name = "urm_role")
    @Enumerated(EnumType.ORDINAL)
    private UserRoles role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "urm_user_id")
    @JsonBackReference
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
