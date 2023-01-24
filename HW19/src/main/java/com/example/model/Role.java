package com.example.model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Role implements GrantedAuthority {
    MANAGER,
    OWNER,
    GUEST;

    @Override
    public String getAuthority() {
        return name();
    }
}
