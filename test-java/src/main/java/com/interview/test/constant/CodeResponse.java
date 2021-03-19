package com.interview.test.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CodeResponse {
  // Success Response
  SUCCESS_CREATE(100, "Successfully Create User"),
  SUCCESS_FETCH(101, "Successfully Fetched"),
  SUCCESS_UPDATE_USER(102, "Successfully to Update Account"),
  SUCCESS_UPDATE_PASSWORD(103, "Successfully to Update Your Password"),

  // Failed Response
  FAIL_CREATE(110, "Failed to Create"),
  FAIL_FETCH(111, "Failed to Fetch"),
  FAIL_UPDATE(112, "Failed to Update"),
  FAIL_NOT_FOUND(113, "Data not found"),
  FAIL_EMAIL_EXIST(114, "Identity Email already exist");

  Integer code;
  String message;
}
