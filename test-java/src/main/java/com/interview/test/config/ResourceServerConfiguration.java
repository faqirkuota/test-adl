package com.interview.test.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.servlet.ServletContext;

@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
  private static final Logger log = LoggerFactory.getLogger(ResourceServerConfiguration.class);

  private static final String RESOURCE_ID = "resource-server-rest-api";
  private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
  private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
  private static final String UNSECURED_PATTERN_USER = "/public/**";
  private static final String SECURED_PATTERN = "/validate/**";

  @Value("${service.resource.secured-pattern}")
  private String securedPattern;

  @Value("${service.oauth-bearer-token-as-basic-auth}")
  private boolean enableBearerTokenOnBasicAuth;

  @Autowired
  private ServletContext servletCtx;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) {
    resources.resourceId(RESOURCE_ID);
  }

  @Override
  public void configure(HttpSecurity http) throws Exception {

    if (securedPattern == null || securedPattern.trim().length() == 0) {
      securedPattern = SECURED_PATTERN;
    }
    String ctxPath = servletCtx.getContextPath();
    if ("/".equals(ctxPath)) {
      ctxPath = "";
    }
    log.info("Using securedPattern=" + ctxPath + securedPattern);
    if (enableBearerTokenOnBasicAuth) {
      http.authorizeRequests()
              .antMatchers(ctxPath + UNSECURED_PATTERN_USER).permitAll()
              .antMatchers(ctxPath + "/health").permitAll()
              .antMatchers(ctxPath + "/token").permitAll()
              .antMatchers(HttpMethod.GET).access(SECURED_READ_SCOPE)
              .antMatchers(HttpMethod.POST).access(SECURED_WRITE_SCOPE)
              .antMatchers(ctxPath + securedPattern).authenticated();
    } else {
      http.authorizeRequests()
              .antMatchers(ctxPath + UNSECURED_PATTERN_USER).permitAll()
              .antMatchers(ctxPath + "/health").permitAll()
              .antMatchers(ctxPath + "/token").permitAll()
              .antMatchers(ctxPath + securedPattern).authenticated();
    }
  }
}
