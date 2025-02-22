package org.example.manage_web.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "auth_user")
public class AuthUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String password;
    @Column(name = "last_login")
    private String lastLogin;
    @Column(name = "is_superuser")
    private int isSuperuser; // boolean에서 int로 변경
    private String username;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "is_staff")
    private boolean isStaff;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "date_joined")
    private String dateJoined;
}