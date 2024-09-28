package com.apple.shop.member;

import com.apple.shop.Sales.Sales;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String display_Name;
    private String password;

    @ToString.Exclude
    @OneToMany(mappedBy = "member") //컬럼명
    List<Sales> sales = new ArrayList<>();
}
