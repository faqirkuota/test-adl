package com.interview.test.config;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.interview.test.constant.CodeResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse<T> {
  private Integer code;
  private String message;
  private T result;

  public GenericResponse successCreate(GenericResponse genericResponse) {
    genericResponse.setCode(CodeResponse.SUCCESS_CREATE.getCode());
    genericResponse.setMessage(CodeResponse.SUCCESS_CREATE.getMessage());
    return genericResponse;
  }

  public GenericResponse successUpdatePassword(GenericResponse genericResponse) {
    genericResponse.setCode(CodeResponse.SUCCESS_UPDATE_PASSWORD.getCode());
    genericResponse.setMessage(CodeResponse.SUCCESS_UPDATE_PASSWORD.getMessage());
    return genericResponse;
  }

  public GenericResponse successUpdateUser(GenericResponse genericResponse) {
    genericResponse.setCode(CodeResponse.SUCCESS_UPDATE_USER.getCode());
    genericResponse.setMessage(CodeResponse.SUCCESS_UPDATE_USER.getMessage());
    return genericResponse;
  }

  public GenericResponse failExistEmail(GenericResponse genericResponse) {
    genericResponse.setCode(CodeResponse.FAIL_EMAIL_EXIST.getCode());
    genericResponse.setMessage(CodeResponse.FAIL_EMAIL_EXIST.getMessage());
    return genericResponse;
  }

  public GenericResponse failNotFound(GenericResponse genericResponse) {
    genericResponse.setCode(CodeResponse.FAIL_NOT_FOUND.getCode());
    genericResponse.setMessage(CodeResponse.FAIL_NOT_FOUND.getMessage());
    return genericResponse;
  }
}
