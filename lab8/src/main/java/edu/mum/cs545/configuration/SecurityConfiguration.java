package edu.mum.cs545.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests().antMatchers("/webjars/**", "/css/**").permitAll()
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout().logoutSuccessUrl("/").logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("admin").roles("FULL")
                .and().withUser("audit").password("audit").roles("AUDIT");
    }
}
