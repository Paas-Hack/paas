package com.namepro.pass.model;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "T_USER_PRONUNCIATION")
public class UserPronunciation {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "uid", nullable = false)
    private User user;

    @Column(name = "PHONETIC_STR")
    private String phoneticString;

    @Column(name = "PRONUNCIATION")
    private byte[] pronunciation;

    @Column(name = "IS_PRIMARY")
    private boolean isPrimary;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "CREATED_TS")
    private LocalDateTime createdTs;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    @Column(name = "UPDATED_TS")
    private LocalDateTime updatedTs;

    @Type(type="org.hibernate.type.BinaryType")
    private byte[] data;
}
