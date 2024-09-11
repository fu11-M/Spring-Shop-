package com.apple.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
        this.myUserDetailsService = myUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")  // 로그인 페이지 경로
//                        .loginProcessingUrl("/login")  // 로그인 요청 URL
//                        .defaultSuccessUrl("/index", true)  // 로그인 성공 후 이동할 URL
//                        .failureUrl("/login?error=true")  // 로그인 실패 후 이동할 URL
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")  // 로그아웃 URL
//                        .logoutSuccessUrl("/login?logout=true")  // 로그아웃 후 이동할 URL
//                )
//                .userDetailsService(myUserDetailsService);  // UserDetailsService 설정
//
//        return http.build();
//    }
//}


// ------------------------------------------------------------------------------------------------------------------
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final MyUserDetailsService myUserDetailsService;
//
//    public SecurityConfig(MyUserDetailsService myUserDetailsService) {
//        this.myUserDetailsService = myUserDetailsService;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/login", "/register", "/resources/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")  // 커스터마이즈된 로그인 페이지 경로
//                        .loginProcessingUrl("/login")  // 로그인 폼의 action URL
//                        .defaultSuccessUrl("/index", true)  // 로그인 성공 시 이동할 페이지
//                        .failureUrl("/login?error=true")  // 로그인 실패 시 이동할 페이지
//                )
//                .logout(logout -> logout
//                        .logoutUrl("/logout")
//                        .logoutSuccessUrl("/login?logout=true")
//                )
//                .userDetailsService(myUserDetailsService);  // 사용자 정보 서비스 설정
//
//        return http.build();
//    }

//-------------------------------------------------------------------------------------------------------------------

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/index", true)
//                )
//                .logout(logout -> logout.logoutUrl("/logout"))
//                .userDetailsService(myUserDetailsService);  // 이 라인 확인
//
//        return http.build();
//    }

    //-------------------------------------------------------------------------------------------------------------------
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/index", true)
//                )
//                .logout(logout -> logout.logoutUrl("/logout"))
//                .userDetailsService(myUserDetailsService);  // 이 라인 확인
//
//        return http.build();
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());

//        http.sessionManagement((session) -> session
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        );

//        http.addFilterBefore(new JwtFilter(), ExceptionTranslationFilter.class);

        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/index",
                                "/mypage",
                                "/write",
                                "/list",
                                "/register",
                                "/login",
                                "/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/index", true)
                )

                .logout(logout -> logout.logoutUrl("/logout"))
                .userDetailsService(myUserDetailsService);

        return http.build();
    }

}
