package com.mj.kimsnote.common.security;

import com.mj.kimsnote.common.jwt.JwtAuthenticationFilter;
import com.mj.kimsnote.common.jwt.JwtTokenProvider;
import com.mj.kimsnote.common.security.exception.AccessDeniedExceptionHandler;
import com.mj.kimsnote.common.security.exception.AuthenticatedExceptionHandler;
import com.mj.kimsnote.common.security.oauth.OAuth2LoginSuccessHandler;
import com.mj.kimsnote.service.member.oauth.CustomOAuth2UserService;
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

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/", "/error/**", "/login/**").permitAll()
                                .requestMatchers("/member/**","/user/**").hasAnyRole("ADMIN", "USER")
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
//                .oauth2Login(oauth2Login->
//                    oauth2Login.successHandler(oAuth2LoginSuccessHandler))
//                        .oauth2Login(oauth2Login->
//                            oauth2Login.userInfoEndpoint(userInfoEndpointConfig ->
//                                userInfoEndpointConfig.userService(customOAuth2UserService))
//                        );


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
