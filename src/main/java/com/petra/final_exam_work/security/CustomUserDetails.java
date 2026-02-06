package com.petra.final_exam_work.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.UUID;

public class CustomUserDetails implements UserDetails {

    private UUID publicUuid;
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(UUID publicUuid, String username, String password, Collection<? extends GrantedAuthority> authorities){
        this.publicUuid = publicUuid;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public UUID getPublicUuid(){
        return publicUuid;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    @Override
    public String getPassword(){
        return password;
    }

    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

}
