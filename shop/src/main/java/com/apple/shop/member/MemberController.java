package com.apple.shop.member;

import com.apple.shop.CustomUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("index")
    String index(){
        return "index.html";
    }

    @GetMapping("/register")
    String register(){
        return "register.html";
    }

    @PostMapping("/member")
    String addMember(@RequestParam String username, @RequestParam String display_Name, @RequestParam String password){
        Member member = new Member();
        member.setUsername(username);
        member.setDisplay_Name(display_Name);

        var hash = passwordEncoder.encode(password);
        member.setPassword(hash);
        memberRepository.save(member);
        return "redirect:/list";
    }

    @GetMapping("/about")
    String aboutPage(){
        return "about.html";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

@GetMapping("/my_page")
public String myPage() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Object principal = authentication.getPrincipal();

    System.out.println("Principal = " + principal.getClass().getName());

    if (principal instanceof CustomUser) {
        CustomUser customUser = (CustomUser) principal;
        System.out.println("사용자 이름: " + customUser.getUsername());
        System.out.println("사용자 Display Name: " + customUser.getDisplay_Name());
        System.out.println("사용자 ID: " + customUser.getId());

        // 인증된 사용자라면 마이페이지를 보여줍니다.
        return "mypage.html";
    } else {
        System.out.println("사용자 정보가 CustomUser가 아닙니다.");
        // 사용자 정보가 CustomUser가 아닌 경우에는 에러 페이지 또는 로그인 페이지로 리다이렉트합니다.
        return "redirect:/login";
    }
}

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser(){
        var a = memberRepository.findById(1);
        var result = a.get();

        var data = new MemberDto(result.getUsername(), result.getDisplay_Name());
        return data; //object를 data에 저장하면 데이터를 자동으로 JSON으로 변환 해준다.
    }

class MemberDto{
    public String username; // public이 있어야 JSON으로 변환 가능
    public String displayName;

    MemberDto(String username, String displayName){
        this.username = username;
        this.displayName = displayName;
    }
}
}