package com.interview.test.domain.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserRequest {

  @NotEmpty(message = "email cant empty")
  @Email
  private String email;

  @NotEmpty(message = "email cant empty")
  private String employeeNumber;
}
