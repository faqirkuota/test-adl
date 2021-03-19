package com.interview.test.config;

import com.interview.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.ServletContext;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@Import(Encoders.class)
public class ServerSecurityConfig extends WebSecurityConfigurerAdapter {
  @Autowired
  private UserService userDetailsService;

  @Autowired
  private PasswordEncoder userPasswordEncoder;

  @Autowired
  private ServletContext servletCtx;

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(userPasswordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String ctxPath = servletCtx.getContextPath();
    if ("/".equals(ctxPath)) {
      ctxPath = "";
    }
    http.csrf().disable().authorizeRequests()
            .antMatchers("/health").permitAll()
            .antMatchers("/**").authenticated();
  }
}
