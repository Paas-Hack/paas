package com.namepro.pass.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "T_USER_LOGIN")
public class UserLogin {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "LAST_LOGIN")
    private LocalDateTime lastLogin;

    @Column(name = "IS_LOGGED_IN")
    private boolean isLoggedIn;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_TS")
    private LocalDateTime createdTs;

}
