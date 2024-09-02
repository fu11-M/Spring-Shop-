//package com.apple.shop;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//@RequiredArgsConstructor
//@Configuration //클래스에 @Configuration, @EnableWebSecurity 어노테이션을 사용하면 Spring Scurity설정을 할 수 있다.
//@EnableWebSecurity
//public class SecurityConfig {
//
//    private final MyUserDetailsService myUserDetailsService;
//
//    @Bean
//    PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//
//    //SecurityFilterChain 메서드는 어떤 페이지를 로그인 검사할지를 설정하는 메서드이다.
//    //FilterChain은 모든 유저의 요청과 서버의 응답 사이에 자동으로 실행해주고 싶은 코드를 담는 곳이다.
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        //CSRF 보안 기능도 끄기 위한 코드
//        //CSRF : API를 하나 만들어두면 다른사이트에서도 내가 만든 API를 요청할 수 있다.
//        //ex 다른사이트.com 에서 <form action = "내사이트.com/write"> 와 같은 형태이다.
//        //이렇게 해두면 다른 사이트에서 나의 사이트에 글을 올릴 수 있는데 이러한 공격을 CSRF공격이라고 한다.
//        //이것을 방지하기 위해서 CSRF 방지 기능을 켜준다.
//        //CSRF 방지 기능을 키게 되면 내사이트에 있는 <form> 태그를 사용하면 서버에서 발급한 랜덤문자를 제공하고
//        //form으로 서버에 제출하게 되면 서버에서 발급한 랜덤문자를 확인하고 서버에서 발급한 것이 맞다면 통과시켜 준다.
//
////        http.csrf((csrf) -> csrf.disable());
////        http.authorizeHttpRequests(authorize -> authorize
////                .requestMatchers("/**").permitAll()  // Allow access to login and register pages
////                .anyRequest().authenticated()  // Require authentication for any other request
////        );
//
////        http.csrf(csrf -> csrf.disable())
////                .authorizeHttpRequests(authorize ->
////                        authorize
////                                .requestMatchers("/**").permitAll()
////                                .anyRequest().authenticated()
////                );
////
////        http.formLogin((formLogin)
////                -> formLogin.loginPage("/login")
////                .defaultSuccessUrl("/index",true)
////        );
////
////        http.logout(logout -> logout.logoutUrl("/logout"));
////        http.userDetailsService(myUserDetailsService);
////        return http.build();
//
//        http.csrf((csrf) -> csrf.disable());
//        http.authorizeHttpRequests((authorize) -> authorize.requestMatchers("/**").permitAll());
//        http.formLogin((formLogin) -> formLogin.loginPage("/login")
//                .defaultSuccessUrl("/index")
//        );
//        return http.build();
//    }
//}

package com.apple.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean // 스프링이 이 코드를 가져가서 bean으로 만들어준다.
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build());
        manager.createUser(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("admin")
                .roles("ADMIN")
                .build());
        return manager;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // 개발 중에는 CSRF 비활성화, 운영 환경에서는 활성화 권장
                .authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/**").hasRole("USER")
                                .anyRequest().permitAll()
                )
                .formLogin(form ->
                        form
                                .loginPage("/login")
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .permitAll()
                );

        //폼으로 로그인
        http.formLogin((formLogin) -> formLogin.loginPage("/login")
                        .defaultSuccessUrl("/index") // 성공시 url
                        //.failureUrl("/fail") // 실패시 기본적으로 /login?error로 이동함 굳이 x
                );

        //로그아웃
        http.logout(logout -> logout.logoutUrl("/logout"));
        return http.build();
    }

}
