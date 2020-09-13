package com.jonny.chat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**加密密码*/
//    @Bean
//    PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user1").password("1").roles("ADMIN")
                .and().withUser("user2").password("1").roles("USER")
                .and().withUser("user3").password("1").roles("USER");
//                .and().passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/webjars/**", "/css/**", "/js/**", "/fonts/**", "/favicon.ico", "/**/favicon.ico", "/auth/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.logout().logoutSuccessUrl("/auth").permitAll();
        http.csrf().disable();
        //防止iframe
        http.headers().frameOptions().disable();
        http.formLogin()
                .loginPage("/auth")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/main")
                .failureUrl("/auth?error=true").permitAll()
                .and().authorizeRequests().antMatchers("/error", "/login").permitAll()
                .and().authorizeRequests().anyRequest().authenticated();
    }

}
