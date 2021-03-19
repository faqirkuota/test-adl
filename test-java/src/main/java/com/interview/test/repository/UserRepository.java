package com.interview.test.repository;

import com.interview.test.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  User findByUsername(String username);

  @Query("SELECT max(u.employeeNumber) FROM User u")
  String getMaxemployeeNumber();

  User findByUsernameOrEmployeeNumber(String username, String employeeNumber);
}
