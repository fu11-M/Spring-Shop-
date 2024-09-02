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

    // @Colum => colum에 대한 설정을 할 수 있다.
    // nullable = false, unique = true => 이 컬럼 값이 유니크하지 않으면 테이블 입력을 막아준다.
//    @Column(nullable = false, unique = true)
    private String title;

    // columDefinition = 컬럼타입을 강제로 지정함
    // String Type은 문자 255자가 한계이지만 TEXT 타입은 몇 만자 이상을 사용할 수 있다.
//    @Column(columnDefinition = "TEXT")
    private Integer price; // 컬럼용 변수에는 int말고 Integer를 강요함

    //컬럼설정 변경사항은 DB에 반영이 자동으로 안될 수 있기 때문에 DB들어가서 테이블 삭제하고 다시 만드는게 빠르다.

    //Object값을 가져올 때 한눈에 알아보기 어려우니 toString과 같은 메소드를 사용하여 Controller에서 사용할 수 있다.
//    public String toString(){
//        return this.title + this.price;
//    }
//    toString과 같은 메소드를 만들기 귀찮을 시 Lombok의 ToString 어노테이션을 사용한다.

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
