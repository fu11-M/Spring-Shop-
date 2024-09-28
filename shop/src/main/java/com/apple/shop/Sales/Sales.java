package com.apple.shop.Sales;

import com.apple.shop.member.Member;
import jakarta.persistence.*;
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
//    private String username;
//    private String displayName;

//    @ManyToOne(fetch = FetchType.EAGER)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT)) // Foreign key 제약 조건 자동x
    private Member member;

    @CreationTimestamp //행이 추가될 때 현재시간을 자동으로 채워준다.
    private LocalDateTime created;
}
