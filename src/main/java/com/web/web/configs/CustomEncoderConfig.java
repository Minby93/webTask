package com.web.web.configs;

import com.web.web.security.CustomEncoder;
import com.web.web.services.EncryptService;
import com.web.web.services.UserDetailServiceImpl;
import com.web.web.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class CustomEncoderConfig {

    private UserDetailServiceImpl userService;
    private EncryptService encryptService;


    @Autowired
    public CustomEncoderConfig(UserDetailServiceImpl userService, EncryptService encryptService){
        this.userService = userService;
        this.encryptService = encryptService;
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // Установили страницу авторизации и выдали на ее получение для всех
                .logout(LogoutConfigurer::permitAll) // Доступ для всех для выхода из аккаунта
                // Настройка доступа
                .authorizeHttpRequests((requests) -> requests
                        // Страницу авторизации сделали доступной только для неавторизованных пользователей
                        .requestMatchers("/user/register").anonymous()
                        .requestMatchers("/error/*").permitAll()
                        .anyRequest().authenticated()

                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .defaultSuccessUrl("/")
                );


        return http.build();

    }




    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(customEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public CustomEncoder customEncoder(){
        return new CustomEncoder(encryptService);
    }
}