//package com.apple.shop;
//
//import com.apple.shop.member.JwtUtil;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//import java.util.Arrays;
//
////@Component
////public class JwtFilter extends OncePerRequestFilter {
//////    @Override
//////    protected void doFilterInternal(
//////            HttpServletRequest request,
//////            HttpServletResponse response,
//////            FilterChain filterChain
//////    ) throws ServletException, IOException {
//////        Cookie[] cookies = request.getCookies();
//////        if (cookies == null){
//////            filterChain.doFilter(request, response);
//////            return;
//////        }
//////
////////        System.out.println(cookies[0].getName());
////////        System.out.println(cookies[0].getValue());
//////
//////        var jwtCookie = "";
//////        for(int i = 0; i < cookies.length; i++){
//////            if(cookies[i].getName().equals("jwt")){
//////                jwtCookie = cookies[i].getValue();
//////            }
//////        }
//////
//////        Claims claim;
//////        try{
//////            claim = JwtUtil.extractToken(jwtCookie);
//////        } catch (Exception e){
//////            filterChain.doFilter(request, response);
//////            return;
//////        }
//////
//////        var array = claim.get("authorities").toString().split(",");
//////        var authorities = Arrays.stream(array).map(a -> new SimpleGrantedAuthority(a)).toList();
//////
//////        var customUser = new CustomUser(claim.get("username").toString(),
//////                "none",
//////                authorities,
//////                claim.get("displayName").toString(),   // display_Name 전달
//////                Integer.parseInt(claim.get("id").toString()) // id 전달
//////                );
//////
//////        customUser.display_Name = claim.get("displayName").toString();
//////        var authToken = new UsernamePasswordAuthenticationToken(
//////            customUser,""
//////        );
//////        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//////        SecurityContextHolder.getContext().setAuthentication(authToken);  // 인증 정보를 SecurityContext에 저장
//////        claim.get("username").toString();
//////
//////        System.out.println("필터가 실행되었습니다.");
//////        System.out.println(jwtCookie);
//////        //요청들어올때마다 실행할코드~~
//////        filterChain.doFilter(request, response);
//////    }
//////-------------------------------------------------------------------------------------------------------
////@Override
////protected void doFilterInternal(
////        HttpServletRequest request,
////        HttpServletResponse response,
////        FilterChain filterChain
////) throws ServletException, IOException {
////    Cookie[] cookies = request.getCookies();
////    if (cookies == null) {
////        filterChain.doFilter(request, response);
////        return;
////    }
////
////    var jwtCookie = "";
////    for (int i = 0; i < cookies.length; i++) {
////        if (cookies[i].getName().equals("jwt")) {
////            jwtCookie = cookies[i].getValue();
////        }
////    }
////
////    Claims claim;
////    try {
////        claim = JwtUtil.extractToken(jwtCookie);
////    } catch (Exception e) {
////        filterChain.doFilter(request, response);
////        return;
////    }
////
////    // claim에서 값 추출 시 null 체크 추가
////    String username = claim.get("username") != null ? claim.get("username").toString() : "defaultUsername";
////    String displayName = claim.get("displayName") != null ? claim.get("displayName").toString() : "defaultDisplayName";
////    Double id = claim.get("id") != null ? Double.parseDouble(claim.get("id").toString()) : -1.0;
////
//////    String authoritiesString = claim.get("authorities") != null ? claim.get("authorities").toString() : "";
//////    var array = authoritiesString.split(",");
////////    var array = claim.get("authorities").toString().split(",");
//////    var authorities = Arrays.stream(array).map(a -> new SimpleGrantedAuthority(a)).toList();
//////
//////    var customUser = new CustomUser(
//////            username,
//////            "none",
//////            authorities,
//////            displayName,
//////            id.intValue()
//////    );
//////
//////    customUser.display_Name = displayName;
//////
//////    var authToken = new UsernamePasswordAuthenticationToken(
//////            customUser, ""
//////    );
//////    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//////    SecurityContextHolder.getContext().setAuthentication(authToken);  // 인증 정보를 SecurityContext에 저장
////
////    String authoritiesString = claim.get("authorities") != null ? claim.get("authorities").toString() : "";
////    if (!authoritiesString.isEmpty()) {
////        var array = authoritiesString.split(",");
////        var authorities = Arrays.stream(array)
////                .filter(auth -> !auth.trim().isEmpty()) // 빈 권한 필터링
////                .map(SimpleGrantedAuthority::new)
////                .toList();
////
////        var customUser = new CustomUser(
////                username,
////                "none",
////                authorities,
////                displayName,
////                id.intValue()
////        );
////
////        customUser.display_Name = displayName;
////
////        var authToken = new UsernamePasswordAuthenticationToken(customUser, "");
////        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
////        SecurityContextHolder.getContext().setAuthentication(authToken);  // 인증 정보를 SecurityContext에 저장
////    }
////
////    System.out.println("필터가 실행되었습니다.");
////    System.out.println(jwtCookie);
////    // 요청 들어올 때마다 실행할 코드
////    filterChain.doFilter(request, response);
////}
//////    -------------------------------------------------------------------------------------------------------
////
////}
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String jwtToken = authHeader.substring(7);
//
//            try {
//                Claims claims = JwtUtil.extractToken(jwtToken);
//                String username = claims.get("username", String.class);
//                String displayName = claims.get("displayName", String.class);
//                Integer id = claims.get("id", Integer.class);
//                String authoritiesString = claims.get("authentication", String.class);
//
//                var authorities = Arrays.stream(authoritiesString.split(","))
//                        .map(SimpleGrantedAuthority::new)
//                        .toList();
//
//                CustomUser customUser = new CustomUser(username, "none", authorities, displayName, id);
//
//                var authToken = new UsernamePasswordAuthenticationToken(customUser, null, authorities);
//                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//                // SecurityContextHolder에 인증 정보 저장
//                SecurityContextHolder.getContext().setAuthentication(authToken);
//                System.out.println("Authentication 설정 완료: " + authToken);
//            } catch (Exception e) {
//                System.out.println("JWT 파싱 오류: " + e.getMessage());
//                filterChain.doFilter(request, response);
//                return;
//            }
//        } else {
//            System.out.println("Authorization 헤더가 없습니다.");
//        }
//
//        // 필터 체인 계속 실행
//        filterChain.doFilter(request, response);
//    }
//}

package com.apple.shop;

import com.apple.shop.member.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwtToken = authHeader.substring(7);

            try {
                Claims claims = JwtUtil.extractToken(jwtToken);
                String username = claims.get("username", String.class);
                String displayName = claims.get("displayName", String.class);
                Integer id = claims.get("id", Integer.class);
                String authoritiesString = claims.get("authentication", String.class);

                var authorities = Arrays.stream(authoritiesString.split(","))
                        .map(SimpleGrantedAuthority::new)
                        .toList();

                CustomUser customUser = new CustomUser(username, "none", authorities, displayName, id);

                var authToken = new UsernamePasswordAuthenticationToken(customUser, null, authorities);
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContextHolder에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Authentication 설정 완료: " + authToken);
            } catch (Exception e) {
                System.out.println("JWT 파싱 오류: " + e.getMessage());
                filterChain.doFilter(request, response);
                return;
            }
        } else {
            System.out.println("Authorization 헤더가 없습니다.");
        }

        // 필터 체인 계속 실행
        filterChain.doFilter(request, response);
    }
}
