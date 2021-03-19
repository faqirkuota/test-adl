package com.interview.test.service;

import com.interview.test.constant.CodeResponse;
import com.interview.test.domain.CommonRole;
import com.interview.test.domain.CommonUserDetails;
import com.interview.test.domain.request.ActiveDeactiveUser;
import com.interview.test.domain.request.NewPasswordRequest;
import com.interview.test.domain.request.NewUserRequest;
import com.interview.test.config.GenericResponse;
import com.interview.test.domain.request.UpdateUserRequest;
import com.interview.test.domain.response.SearchUserResponse;
import com.interview.test.model.User;
import com.interview.test.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {
  private static final GenericResponse genericResponse = new GenericResponse();

  @Autowired
  UserRepository userRepository;
  private final PasswordEncoder userPasswordEncoder;


  @Override
  @Transactional(readOnly = true)
  @Cacheable("userByUsername")
  public CommonUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userRepository.findByUsername(username);
    if (user != null) {
      CommonUserDetails userDetails = new CommonUserDetails();
      BeanUtils.copyProperties(user, userDetails);
      List<CommonRole> roles = new ArrayList<CommonRole>();
      userDetails.setAuthorities(roles);
      return userDetails;
    }
    throw new UsernameNotFoundException(username);
  }

  @SneakyThrows
  public GenericResponse newUser(NewUserRequest request) {
    User checkUser = userRepository.findByUsername(request.getEmail());
    if (checkUser != null) {
      return genericResponse.failExistEmail(genericResponse);
    } else {
      User newUser = new User();
      newUser.setUsername(request.getEmail());
      newUser.setPassword(userPasswordEncoder.encode(request.getPassword()));
      newUser.setEmployeeNumber(employeeNumberGenerator());
      newUser.setBirthPlace(request.getBirthPlace());
      newUser.setBirthDate(request.getBirthDate());
      userRepository.save(newUser);
      return genericResponse.successCreate(genericResponse);
    }
  }

  @SneakyThrows
  public GenericResponse newPassword(NewPasswordRequest request) {
    User existUser = userRepository.findByUsername(request.getEmail());
    if (existUser == null) {
      return genericResponse.failNotFound(genericResponse);
    } else {
      existUser.setPassword(userPasswordEncoder.encode(request.getPassword()));
      userRepository.save(existUser);
      return genericResponse.successUpdatePassword(genericResponse);
    }
  }

  @SneakyThrows
  public GenericResponse<SearchUserResponse> searchUser(String email, String employeeNumber) {
    User existUser = userRepository.findByUsernameOrEmployeeNumber(email, employeeNumber);
    if (existUser == null) {
      return genericResponse.failNotFound(genericResponse);
    } else {
      SearchUserResponse searchUserResponse = SearchUserResponse.builder()
              .email(existUser.getUsername())
              .employeeNumber(existUser.getEmployeeNumber())
              .birthPlace(existUser.getBirthPlace())
              .birthDate(existUser.getBirthDate())
              .build();
      GenericResponse genericResponse = GenericResponse.builder()
              .code(CodeResponse.SUCCESS_FETCH.getCode())
              .message(CodeResponse.SUCCESS_FETCH.getMessage())
              .result(searchUserResponse)
              .build();
      return genericResponse;
    }
  }

  @SneakyThrows
  public GenericResponse<Iterable<User>> findAllUser(PageRequest pageRequest) {
    GenericResponse genericResponse = GenericResponse.builder()
            .code(CodeResponse.SUCCESS_FETCH.getCode())
            .message(CodeResponse.SUCCESS_FETCH.getMessage())
            .result(userRepository.findAll(pageRequest))
            .build();
    return genericResponse;
  }

  @SneakyThrows
  public GenericResponse updateUser(UpdateUserRequest request) {
    User dataUser = userRepository.findByUsername(request.getEmail());
    if (dataUser == null) {
      return genericResponse.failNotFound(genericResponse);
    } else {
      dataUser.setBirthPlace(request.getBirthPlace());
      dataUser.setBirthDate(request.getBirthDate());
      userRepository.save(dataUser);
      return genericResponse.successUpdateUser(genericResponse);
    }
  }

  @SneakyThrows
  public GenericResponse deactiveUser(ActiveDeactiveUser request) {
    User dataUser = userRepository.findByUsername(request.getEmail());
    if (dataUser == null) {
      return genericResponse.failNotFound(genericResponse);
    } else {
      dataUser.setEnabled(request.isStatus());
      userRepository.save(dataUser);
      return genericResponse.successUpdateUser(genericResponse);
    }
  }

  @SneakyThrows
  private String employeeNumberGenerator() {
    Date nowDate = new Date();
    SimpleDateFormat sdfNewFormat = new SimpleDateFormat("yyyy");
    String nowYear = sdfNewFormat.format(nowDate);
    String lastEmployee = userRepository.getMaxemployeeNumber();
    Integer inc = Integer.parseInt(lastEmployee.substring(4).replaceFirst("^0+(?!$)", ""));
    String padded = String.format("%03d", inc + 1);
    return nowYear + padded;
  }
}
