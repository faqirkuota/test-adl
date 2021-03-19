package com.interview.test.controller;

import com.interview.test.config.GenericResponse;
import com.interview.test.domain.request.ActiveDeactiveUser;
import com.interview.test.domain.request.NewPasswordRequest;
import com.interview.test.domain.request.NewUserRequest;
import com.interview.test.domain.request.SearchUserRequest;
import com.interview.test.domain.request.UpdateUserRequest;
import com.interview.test.domain.response.SearchUserResponse;
import com.interview.test.model.User;
import com.interview.test.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.bytebuddy.dynamic.TypeResolutionStrategy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import sun.java2d.pipe.hw.AccelTypedVolatileImage;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
public class UserController {

  private UserService userService;

  @PostMapping(path = "/newUser")
  public ResponseEntity<GenericResponse> newUser(@RequestBody @Valid NewUserRequest request) {
    GenericResponse response = userService.newUser(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping(path = "/newPassword")
  public ResponseEntity<GenericResponse> newPassword(@RequestBody @Valid NewPasswordRequest request) {
    GenericResponse response = userService.newPassword(request);
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/searchUser")
  public ResponseEntity<GenericResponse<SearchUserResponse>> searchUser(@RequestParam(value = "email", defaultValue = "", required = false) String email,
                                                                        @RequestParam(value = "employeeNumber", defaultValue = "", required = false) String employeeNumber) {
    GenericResponse<SearchUserResponse> response = userService.searchUser(email, employeeNumber);
    return ResponseEntity.ok(response);
  }

  @GetMapping(path = "/getAllUser")
  public ResponseEntity<GenericResponse<Iterable<User>>> findAll(
          @RequestParam(value = "page", defaultValue = "0", required = false) int page,
          @RequestParam(value = "count", defaultValue = "10", required = false) int count) {
    GenericResponse<Iterable<User>> response = userService.findAllUser(PageRequest.of(page, count));
    return ResponseEntity.ok(response);
  }

  @PostMapping(path = "/updateDataUser")
  public ResponseEntity<GenericResponse> updateUser(@RequestBody @Valid UpdateUserRequest request) {
    GenericResponse response = userService.updateUser(request);
    return ResponseEntity.ok(response);
  }

  @PostMapping(path = "/userActiveControl")
  public ResponseEntity<GenericResponse> controlUser(@RequestBody @Valid ActiveDeactiveUser request) {
    GenericResponse response = userService.deactiveUser(request);
    return ResponseEntity.ok(response);
  }
}
