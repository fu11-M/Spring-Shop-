package com.apple.shop;
import com.apple.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var result = memberRepository.findByUsername(username);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("아이디가 존재하지 않습니다.");
        }

        var user = result.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반 유저"));

        CustomUser customUser = new CustomUser(user.getUsername(), user.getPassword(), authorities, user.getDisplay_Name(), user.getId());
        System.out.println("Returning CustomUser: " + customUser.getClass().getName()); // 추가된 로그
        customUser.id = user.getId();
        return customUser;
    }
}

