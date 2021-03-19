package com.interview.test.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserRequest implements Serializable {
  @NotEmpty(message = "email cant empty")
  @Email
  private String email;

  @NotEmpty(message = "password cant empty")
  private String password;

  @NotEmpty(message = "birthPlace cant empty")
  private String birthPlace;

  @NotNull
  private Date birthDate;
}
