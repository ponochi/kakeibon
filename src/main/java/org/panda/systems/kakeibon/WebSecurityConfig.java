package org.panda.systems.kakeibon;

import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Import(AppConfig.class)
public class WebSecurityConfig {

    @Autowired
    DataSource dataSource;
    @Autowired
    CustomUserDetailsService customUsersDetailsService;

//    public WebSecurityConfig() {
////        this.dataSource = null;
//        this.customUsersDetailsService = null;
//    }
//    @Autowired
//    public WebSecurityConfig(
////            DataSource dataSource,
//            CustomUserDetailsService customUsersDetailsService) {
////        this.dataSource = dataSource;
//        this.customUsersDetailsService = customUsersDetailsService;
//    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories
                .createDelegatingPasswordEncoder();
    }

//  private CustomUserDetails makeUser(String username, String password, String role) {
//    return User
//        .withUsername(username)
//        .password(new BCryptPasswordEncoder().encode(password))
//        .roles(role)
//        .disabled(false)
//        .build();
//  }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

//        http
        http.csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/")
                        )
                )
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations().toString()).permitAll()
                        .requestMatchers("/css/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/js/common/include/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/js/common/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/js/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/image/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/users/**").hasRole("ADMIN")
                        .requestMatchers("/common/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/currency/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/spec/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/**").hasAnyRole("ADMIN", "USER")
                        .anyRequest().authenticated()
                ).formLogin(login -> login
//                        .loginPage("/myLogin")
//                        .loginProcessingUrl("/sign_in")
//                        .usernameParameter("username")
//                        .passwordParameter("password")
                                .defaultSuccessUrl("/menu/mainMenu", true)
                                .failureUrl("/login?error=true")
                ).logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login")
//                ).rememberMe(rememberMe -> rememberMe
//                        .key("remember-me")
//                        .tokenValiditySeconds(86400)
                ).userDetailsService(customUsersDetailsService);

        return http.build();
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder();
    }
}
