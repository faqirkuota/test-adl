package com.interview.test.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "userId")
public class CommonUserDetails implements UserDetails, Serializable {
  private static final long serialVersionUID = 1L;

  private UUID userId;

  private String fullName;

  private String username;

  private String password;

  private boolean enabled;

  private boolean accountExpired;

  private boolean accountLocked;

  private boolean credentialsExpired;

  private UUID createdBy;

  private Date createdTime;

  private UUID updatedBy;

  private Date updatedTime;

  private Collection<CommonRole> authorities;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    if (authorities == null) {
      return Collections.emptyList();
    }
    return authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return !this.accountExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return !this.accountLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return !this.credentialsExpired;
  }

  @Override
  public boolean isEnabled() {
    return this.enabled;
  }

  @Override
  public String toString() {
    return "User [userId=" + userId + ", fullName=" + fullName + ", username=" + username
            + ", enabled=" + enabled
            + ", accountExpired=" + accountExpired + ", accountLocked=" + accountLocked + ", credentialsExpired="
            + credentialsExpired + "]";
  }
}
