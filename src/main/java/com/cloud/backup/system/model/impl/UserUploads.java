package com.cloud.backup.system.model.impl;


import com.cloud.backup.system.model.Model;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

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

    @CreationTimestamp
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


    public String getName() {
        return name;
    }

    public LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public LocalDateTime getRemovedDate() {
        return removedDate;
    }

    public String getMimeType() {
        return mimeType;
    }

    public String getFolder() {
        return folder;
    }

    public UserUploads setId(Long id) {
        this.id = id;
        return this;
    }

    public UserUploads setUser(User user) {
        this.user = user;
        return this;
    }

    public UserUploads setUnsafe(Boolean unsafe) {
        isUnsafe = unsafe;
        return this;
    }

    public UserUploads setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public UserUploads setName(String name) {
        this.name = name;
        return this;
    }

    public UserUploads setMimeType(String mimeType) {
        this.mimeType = mimeType;
        return this;
    }

    public UserUploads setFolder(String folder) {
        this.folder = folder;
        return this;
    }

    public UserUploads setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
        return this;
    }

    public UserUploads setRemovedDate(LocalDateTime removedDate) {
        this.removedDate = removedDate;
        return this;
    }
}
