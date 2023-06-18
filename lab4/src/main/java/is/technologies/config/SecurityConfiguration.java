package is.technologies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for Spring Security.
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  /**
   * Configures the password encoder for authentication.
   * @return The configured password encoder.
   */
  @Bean(name = "pwdEncoder")
  public PasswordEncoder getPasswordEncoder() {
    DelegatingPasswordEncoder delPasswordEncoder = (DelegatingPasswordEncoder) PasswordEncoderFactories
        .createDelegatingPasswordEncoder();
    BCryptPasswordEncoder bcryptPasswordEncoder = new BCryptPasswordEncoder();
    delPasswordEncoder.setDefaultPasswordEncoderForMatches(bcryptPasswordEncoder);
    return delPasswordEncoder;
  }

  /**
   * Configures HTTP security settings.
   * @param http The HttpSecurity object to be configured.
   * @throws Exception If an error occurs while configuring HTTP security.
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic().and().csrf().disable().authorizeRequests()
        .antMatchers("/user/**").hasRole("ADMIN")
        .antMatchers("/street-entity/**").hasAnyRole("USER", "ADMIN")
        .antMatchers("/house-entity/**").hasAnyRole("USER", "ADMIN")
        .antMatchers("/apartment-entity/**").hasAnyRole("USER", "ADMIN")
        .antMatchers("/**").hasAnyRole("USER", "ADMIN")
        .and().formLogin();
  }
}
