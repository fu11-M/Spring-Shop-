package com.apple.shop.Sales;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Sales {
    // 다른 테이블에 이미 있는 컬럼은 빼는 것이 낫고
    // 테이블 핵심주제와 관련 없는 컬럼은 빼는 것이 낫고
    // 정확도 필요 없는 컬럼은 그대로 놔둔다.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String itemName;
    private Integer price;
    private Integer count;
//    String username;
//    String displayName;
    private Integer memberId;
    @CreationTimestamp //행이 추가될 때 현재시간을 자동으로 채워준다.
    private LocalDateTime created;
}
