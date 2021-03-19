package com.interview.test.domain;

import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.UUID;

public class CommonRole implements GrantedAuthority, Serializable {
  private static final long serialVersionUID = 5469245326651087272L;

  private UUID roleId;

  private String roleNo;

  private String roleName;

  @Override
  public String getAuthority() {
    return "ROLE_" + this.roleNo;
  }

  @Override
  public String toString() {
    return "CommonRole [roleId=" + roleId + ", roleNo=" + roleNo + ", roleName=" + roleName + "]";
  }
}
