package com.apple.shop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {
    public String display_Name;
    public Integer id;

    //    public Integer id;
    public CustomUser(
            String username,
            String password,
            Collection<? extends GrantedAuthority> authorities,
            String display_Name,
            Integer id
            ) {
        super(username, password, authorities);
        this.display_Name = display_Name;
        this.id = id;
    }
}