package com.apple.shop.member;

import com.apple.shop.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    String register(){
        return "register.html";
    }

    @PostMapping("/member")
    String addMember(@RequestParam String username, @RequestParam String display_Name, @RequestParam String password){
        Member member = new Member();
        member.setUsername(username);
        member.setDisplay_Name(display_Name);
        //매번 member API를 사용할 때 new 키워드로 객체를 생성하는데 이것은 비효율적다.
        // 때문에 object를 스프링이 뽑게하고 dependency injection으로 넣어도 된다.
        // 그 방법은 클래스에 @Service @Component등을 붙이고 원하는 곳에 등록하고 사용하면 된다.
        // 하지만 BCryptPasswordEncoder()는 외부 라이브러리이기 때문에 직접 찾아가서 원하는 곳에 어노테이션을 등록할 수 없다.
        // 하여 누가 만들어 놓은 클래스에 dependency injection으로 사용하고 싶다면 @bean을 사용하면 된다.
        // var hash = new BCryptPasswordEncoder().encode(password);

        var hash = passwordEncoder.encode(password);
        member.setPassword(hash);
        memberRepository.save(member);
        return "redirect:/list";
    }

    @GetMapping("/login")
    public String login(){
        return "login.html";
    }

    @PostMapping("/loginCheck")
    public String loginCheck(@RequestParam String username, @RequestParam String password){
        var result = memberRepository.findByUsername(username);
        //아이디 비교
        if(result.isPresent()){
            var user = result.get();
            if(new BCryptPasswordEncoder().matches(password, user.getPassword())){
                System.out.println("로그인 성공 : " + user.getDisplay_Name());
                return "index.html";
            }
        }
        System.out.println("아이디나 비밀번호가 틀립니다.");
        return "redirect:/login?error?";
    }

    @GetMapping("/my_page")
    public String my_page(){
//        // Authentication 객체가 null인지 확인
//        if (authentication == null || !authentication.isAuthenticated()) {
//            return "register.html";
//        }
//
//        // Principal에서 사용자 정보를 가져옵니다.
//        CustomUser customUser = (CustomUser) authentication.getPrincipal();
//
//        // 사용자 정보 출력
//        System.out.println("사용자 이름: " + customUser.getUsername());
//        System.out.println("사용자 Display Name: " + customUser.getDisplay_Name());
//        System.out.println("인증된 사용자: " + authentication.isAuthenticated());
//
//        // 권한 체크
//        boolean isUser = authentication.getAuthorities().stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("일반 유저"));
//        System.out.println("일반 유저 권한 여부: " + isUser);
//
//        // 인증된 사용자라면 마이페이지를 보여줍니다.
//        return isUser ? "mypage.html" : "register.html";
//    }

//        CustomUser customUser = (CustomUser)authentication.getPrincipal();
//        System.out.println(authentication);
//        System.out.println(authentication.getName());
//        System.out.println(authentication.isAuthenticated());
//        CustomUser result = (CustomUser)authentication.getPrincipal();
//        System.out.println(result.getDisplay_Name());
//
//        System.out.println(authentication.getAuthorities().contains(new SimpleGrantedAuthority("일반 유저")));
//        //로그인한 유저만 페이지 보여주기
//        if (authentication.isAuthenticated()){
//            return "mypage.html";
//        }else{
//            return "register.html";
//        }
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//         //인증된 사용자가 있는지 확인합니다.
//        if (authentication != null && authentication.isAuthenticated()) {
//            // Principal에서 사용자 정보를 가져옵니다.
//            CustomUser customUser = (CustomUser) authentication.getPrincipal();
//
//            // 사용자 정보 출력
//            System.out.println("사용자 이름: " + customUser.getUsername());
//            System.out.println("사용자 Display Name: " + customUser.display_name);
//            System.out.println("인증된 사용자: " + authentication.isAuthenticated());
//
//            // 권한 체크
//            boolean isUser = authentication.getAuthorities()
//                    .contains(new SimpleGrantedAuthority("일반 유저"));
//            System.out.println("일반 유저 권한 여부: " + isUser);
//
//            // 인증된 사용자라면 마이페이지를 보여줍니다.
//            return "mypage.html";
//        } else {
//            // 인증되지 않은 경우 회원가입 페이지를 보여줍니다.
//            return "register.html";
//        }

        //----------------------------------------------------------------------------

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();

        System.out.println("Principal = " + principal.getClass().getName());

        if (principal instanceof CustomUser) {
            CustomUser customUser = (CustomUser) principal;
            System.out.println("사용자 이름: " + customUser.getUsername());
            System.out.println("사용자 Display Name: " + customUser.getDisplay_Name());
            System.out.println("사용자 ID: " + customUser.getId());
        } else {
            System.out.println("사용자 정보가 CustomUser가 아닙니다.");
        }

        if (authentication.isAuthenticated()) {
            return "mypage.html";
        } else {
            return "register.html";
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
}

//Data trancefer object 데이터 변환
//object를 변환해서 전송하려면 Map또는 DTO클래스를 사용하여야 한다.
//Map말고 DTO를 쓰는 이유는 보내는 데이터의 타입체크가 쉽다. // 재사용이 쉽다.
//DTO를 너무 많이 써야 하는 경우에는 Mapping라이브러리를 사용하면 object끼리 변환이 쉬워진다.
class MemberDto{
    public String username; // public이 있어야 JSON으로 변환 가능
    public String displayName;

    MemberDto(String username, String displayName){
        this.username = username;
        this.displayName = displayName;
    }
}