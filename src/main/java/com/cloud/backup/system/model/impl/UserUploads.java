package com.cloud.backup.system.model.impl;


import com.cloud.backup.system.model.Model;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "cloud", name = "TB_USER_UPLOADS")
public class UserUploads implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @Column(name = "is_unsafe")
    private Boolean isUnsafe = false;

    @Column(name = "uuid", nullable = false, updatable = false)
    private UUID uuid;

    @Column
    private String name;

    @Column(updatable = false)
    private LocalDateTime uploadDate;

    @Column
    private LocalDateTime removedDate;

    @Column(name = "stream_type")
    @Enumerated(EnumType.STRING)
    private StreamType streamType;

    @Override
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Boolean getUnsafe() {
        return isUnsafe;
    }

    public UUID getUuid() {
        return uuid;
    }
}
