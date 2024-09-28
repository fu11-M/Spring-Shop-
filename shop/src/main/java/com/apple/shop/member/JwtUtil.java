//package com.apple.shop.member;
//
//import com.apple.shop.CustomUser;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.util.Date;
//import java.util.stream.Collectors;
//
//@Component
//public class JwtUtil {
//    static final SecretKey key =
//            Keys.hmacShaKeyFor(Decoders.BASE64.decode(
//                    "jwtpassword123jwtpassword123jwtpassword123jwtpassword123jwtpassword"
//            ));
//
//    // JWT 만들어주는 함수
//    //유저 정보 JWT
//    public static String createToken(Authentication auth) {
//        var user = (CustomUser) auth.getPrincipal();
//        System.out.println(user);
//        //문자변환
//        var authorities = auth.getAuthorities().stream().map(a-> a.getAuthority()).collect(Collectors.joining(","));
//        System.out.println(authorities);
//        String jwt = Jwts.builder()
//                .claim("username", user.getUsername())
//                .claim("displayName", user.getDisplay_Name())
//                .claim("id", user.getId().intValue())
//                .claim("authentication", authorities)
////                .claim("authentication", user.getAuthorities())
//                .issuedAt(new Date(System.currentTimeMillis()))
//                .expiration(new Date(System.currentTimeMillis() + 1000000)) //유효기간 10초
//                .signWith(key)
//                .compact();
//
//        System.out.println(jwt);
//        return jwt;
//    }
//
//    // JWT 까주는 함수
//    public static Claims extractToken(String token) {
//        Claims claims = Jwts.parser().verifyWith(key).build()
//                .parseSignedClaims(token).getPayload();
//        return claims;
//    }
//}
//
