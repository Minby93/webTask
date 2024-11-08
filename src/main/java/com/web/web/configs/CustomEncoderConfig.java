package com.web.web.configs;

import com.web.web.security.CustomEncoder;
import com.web.web.services.EncryptService;
import com.web.web.services.UserDetailServiceImpl;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;


@Configuration
@EnableWebSecurity
public class CustomEncoderConfig {

    private UserDetailServiceImpl userDetailService;
    private EncryptService encryptService;


    @Autowired
    public CustomEncoderConfig(UserDetailServiceImpl userService, EncryptService encryptService){
        this.userDetailService = userService;
        this.encryptService = encryptService;
    }

    @Value("${front.url}")
    String frontUrl;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    var corsConfiguration = new CorsConfiguration();
                    corsConfiguration.setAllowedOrigins(List.of(frontUrl));
                    corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    corsConfiguration.setAllowedHeaders(List.of("*"));
                    corsConfiguration.setAllowCredentials(true);
                    return corsConfiguration;
                }))
                .logout(logout -> logout
                        .logoutUrl("/logout")  // URL для выхода
                        .clearAuthentication(true)  // Очистка аутентификации
                        .invalidateHttpSession(true)  // Инвалидация сессии
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl(frontUrl + "/login")  // Перенаправление после выхода
                )
                // Настройка доступа
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/login", "/register", "/logout").permitAll()
                        .anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl(frontUrl + "/home")
                        .failureUrl(frontUrl + "/login?error=true")
                );


        return http.build();

    }




    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(customEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        return daoAuthenticationProvider;
    }

    @Bean
    public CustomEncoder customEncoder(){
        return new CustomEncoder(encryptService);
    }



}