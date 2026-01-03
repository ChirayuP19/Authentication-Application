package com.techChirayu.Auth.Auth_Application.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

//    @Bean
//   public UserDetailsService user(){
//       User.UserBuilder userbuilder = User.withDefaultPasswordEncoder();
//
//       UserDetails user1 = userbuilder
//               .username("chirayu")
//               .password("chirayu123")
//               .roles("ADMIN")
//               .build();
//
//       UserDetails user2 = userbuilder
//               .username("kavya")
//               .password("kavya123")
//               .roles("USER")
//               .build();
//        return  new InMemoryUserDetailsManager(user1,user2);
//   }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(x->x.disable())
                .authorizeHttpRequests(authorize->
                        authorize.requestMatchers("/api/v1/auth/registor")
                                .permitAll()
                                .requestMatchers("/api/v1/auth/login").permitAll()
                                .anyRequest()
                                .authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}

