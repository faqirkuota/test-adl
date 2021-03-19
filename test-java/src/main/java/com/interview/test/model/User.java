package com.interview.test.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_name"})})
@EqualsAndHashCode(of = "userId")
public class User implements Serializable {
  private static final long serialVersionUID = 30428952001905983L;

  @Id
  @Column(name = "user_id")
  @JsonIgnore
  private UUID userId = java.util.UUID.randomUUID();

  @Column(name = "user_name")
  private String username;

  @Column(name = "enabled")
  private boolean enabled = true;

  @JsonIgnore
  private String password;

  private String employeeNumber;

  private String birthPlace;

  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date birthDate;

  @Override
  public String toString() {
    return "User [userId=" + userId + ", username=" + username
            + ", enabled=" + enabled
            + ", employeeNumber=" + employeeNumber + ", birthPlace=" + birthPlace + ", birthDate="
            + birthDate + "]";
  }
}
