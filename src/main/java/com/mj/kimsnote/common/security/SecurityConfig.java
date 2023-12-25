package com.mj.kimsnote.common.security;

import com.mj.kimsnote.common.config.CorsConfig;
import com.mj.kimsnote.common.jwt.JwtAuthenticationFilter;
import com.mj.kimsnote.common.jwt.JwtTokenProvider;
import com.mj.kimsnote.common.security.exception.AccessDeniedExceptionHandler;
import com.mj.kimsnote.common.security.exception.AuthenticatedExceptionHandler;
import com.mj.kimsnote.service.member.read.OAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final OAuth2MemberService oAuth2MemberService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/", "/api/member/**","/error").permitAll()
                                .requestMatchers("/user/**", "/api/**").authenticated()
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                )

                .addFilterBefore(
                        new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class
                );

        http
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(new AuthenticatedExceptionHandler())
                                .accessDeniedHandler(new AccessDeniedExceptionHandler())
                );

//        http
//                .formLogin(login ->
//                        login.loginPage("/login")
//                                .loginProcessingUrl("/login")
//                                .usernameParameter("email")
//                                .passwordParameter("password")
//                                .defaultSuccessUrl("/")
//                                .permitAll()
//                ).httpBasic(AbstractHttpConfigurer::disable);
//        http
//                .logout(logout ->
//                        logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
//                                .logoutSuccessUrl("/login")
//                        )

        return http.build();
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        // BCrypt Encoder 사용
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
