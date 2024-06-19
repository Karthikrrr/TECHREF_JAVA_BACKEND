package com.business_website.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.business_website.service_implementation.CustomSucessHandler;
import com.business_website.service_implementation.CustomUserDetailService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomSucessHandler customSucessHandler;

    @Autowired
    CustomUserDetailService customUserDetailService;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
         
        httpSecurity.csrf(c -> c.disable())
        .authorizeHttpRequests(request -> request.requestMatchers("/admin-page")
        .hasAuthority("ADMIN")
        .requestMatchers("admin-page/projects")
        .hasAuthority("ADMIN")
        .requestMatchers("/admin-page/projects/new")
        .hasAuthority("ADMIN")
        .requestMatchers("admin-page/projects/update/{id}")
        .hasAuthority("ADMIN")
        .requestMatchers("admin-page/projects/delete/{id}")
        .hasAuthority("ADMIN")
        .requestMatchers("admin-page/projects/confirmDelete/{id}")
        .hasAuthority("ADMIN")
        .requestMatchers("/")
        .hasAuthority("USER")
        .requestMatchers("/register", "/css/**").permitAll()
        .anyRequest().authenticated())

        .formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login")
        .successHandler(customSucessHandler).permitAll())

        .logout(form -> form.invalidateHttpSession(true).clearAuthentication(true)
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        .logoutSuccessUrl("/login?logout").permitAll());

        return httpSecurity.build();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

}
