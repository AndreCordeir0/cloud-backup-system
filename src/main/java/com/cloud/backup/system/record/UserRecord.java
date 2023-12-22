package com.cloud.backup.system.record;

import com.cloud.backup.system.model.impl.UserRoles;

public record UserRecord(
        Long id,
        String email,
        String name,
        UserRoles userRoles,
        boolean isActive
){ }
