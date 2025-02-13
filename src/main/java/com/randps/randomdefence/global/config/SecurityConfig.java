package com.randps.randomdefence.global.config;

import com.randps.randomdefence.domain.user.domain.UserRepository;
import com.randps.randomdefence.domain.user.service.PrincipalDetailsService;
import com.randps.randomdefence.global.config.filter.JwtRefreshAuthFilter;
import com.randps.randomdefence.global.jwt.JwtProvider;
import com.randps.randomdefence.global.jwt.JwtRefreshUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;

    private final PrincipalDetailsService principalDetailsService;

    private final CorsConfig corsConfig;

    private final UserRepository userRepository;

    private final JwtRefreshUtil jwtUtil;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtProvider jwtTokenProvider() {
        return new JwtProvider(userRepository);
    }

    // 회원의 패스워드 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){

        return new BCryptPasswordEncoder();
    }

    // 시큐리티 필터 설정
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeRequests()
//                .anyRequest().permitAll()
                .antMatchers(HttpMethod.OPTIONS, "/api/v1/**").permitAll()
                .antMatchers("/api/v1/user/auth/login", "/api/v1/user/auth/logout", "/api/v1/user/add/all", "/api/v1/user/admin/init").permitAll()
                .antMatchers("/api/v1/user/add", "/api/v1/user/del", "/api/v1/scraping/*", "api/v1/admin/*").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .cors().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable() // http의 기본 인증. ID, PW 인증방식
//                .authorizeRequests()
//                .antMatchers("/api/v1/auth/*").permitAll()
//                .antMatchers("/api/v1/*").hasRole("USER")
//                .and()
//                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JwtRefreshAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);
//                .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtTokenProvider()))  // JWT 필터 AuthenticationManager
//                .addFilter(new JwtAuthorizationFilter(authenticationManager(),  jwtTokenProvider(), principalDetailsService));  // JWT 필터 AuthenticationManager
//                .authorizeHttpRequests()
//                .anyRequest().denyAll();
//                .antMatchers("/api/v1/auth/*").permitAll()
//                .antMatchers("/api/v1/*").hasRole("USER");
//                .authorizeHttpRequests()
//                .anyRequest().permitAll();

        return http.build();

    }
}
