package com.apple.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString // toString함수를 자동으로 만들어준다.
@Getter
@Setter
@Table(indexes = @Index(columnList = "title", name = "작명"))
//@NoArgsConstructor 기본 생성자 생성 어노테이션
public class Item { // Item이라는 이름으로 테이블을 생성한다.
    //컬럼
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) //id값을 자동으로 1씩 증가
    public Integer id;

    //@Column(nullable = false, unique = true)
    private String title;

    // String Type은 문자 255자가 한계이지만 TEXT 타입은 몇 만자 이상을 사용할 수 있다.
    // @Column(columnDefinition = "TEXT")
    private Integer price; // 컬럼용 변수에는 int말고 Integer를 강요함

    private String imageUrl;

    public Item() {
        // 기본 생성자
    }

    public Item(String title, Integer price, String imageUrl) {
        this.title = title;
        this.price = price;
        this.imageUrl = imageUrl;
    }
}
