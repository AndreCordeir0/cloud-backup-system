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

    @Column(name = "upload_date", updatable = false)
    private LocalDateTime uploadDate;

    @Column(name = "removed_date")
    private LocalDateTime removedDate;

    @Column(name = "mime_type")
    private String mimeType;

    @Column
    private String folder;

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

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUnsafe(Boolean unsafe) {
        isUnsafe = unsafe;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
