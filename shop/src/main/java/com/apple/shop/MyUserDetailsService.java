package com.apple.shop;

import com.apple.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
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
//        DB에서 username을 가진 유저를 찾아와서 return new User(유저아이디, 비번, 권한) 해주세요
        var result = memberRepository.findByUsername(username);
        System.out.println(result);

        if(result.isEmpty()){
            throw new UsernameNotFoundException("아이디가 존재 하지 않습니다.");
        }

        var user = result.get();
        System.out.println(user);

        //권한부여
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반 유저"));

//        var customUser = new CustomUser(user.getUsername(), user.getPassword(), authorities);
//        customUser.setDisplayName(user.getDisplayName());  // setDisplayName() 사용
//        return customUser;

//        return new CustomUser(user.getUsername(),
//                user.getPassword(),
//                authorities,
//                user.getDisplay_Name(),
//                user.getId()
//                );

//        return customUser;

        return new User(user.getUsername(), user.getPassword(), authorities);

    }
    }
