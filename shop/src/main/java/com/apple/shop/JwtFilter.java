//package com.apple.shop;
//
//import com.apple.shop.member.JwtUtil;
//import io.jsonwebtoken.Claims;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
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
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//        System.out.println("Authorization 헤더 값: " + authHeader);  // 헤더 값을 출력하여 확인
//
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            String jwtToken = authHeader.substring(7);
//
//            try {
//                Claims claims = JwtUtil.extractToken(jwtToken);
//                System.out.println("JWT Claims" + claims);
//                String username = claims.get("username", String.class);
//                String displayName = claims.get("displayName", String.class);
//                Integer id = claims.get("id", Double.class).intValue();
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
