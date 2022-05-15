package com.namepro.pass.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "T_USER")
public class User {

    @Id
    private String uid;

    @Column(name = "LAN_ID")
    private String lanId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "PHONE")
    private String phone;

    @Column(name = "WORK_LOC")
    private String workLoc;

    @Column(name = "DOJ")
    private LocalDate doj;

    @Column(name = "IS_ADMIN")
    private boolean isAdmin;

    @Column(name = "IS_SUBSCRIBED")
    private boolean isSubscribed;
}
