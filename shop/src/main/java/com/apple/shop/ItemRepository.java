package com.apple.shop;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Integer> { //<Entity, id컬럼타입>
    // 내부적으로 같은 이름을 가진 클래스를 만들어줌 => class ItemRepository
    // class ItemRepository 에는 DB 입출력하는 함수가 잔뜩 들어가 있음
    Page<Item> findPageBy(Pageable page);
    List<Item> findAllByTitleContains(String title);

//    @Query(value = "select * from item where id = ?1", nativeQuery = true)
    @Query(value = "select * from item where match(title) against(?1)", nativeQuery = true)
    List<Item> rawQuery1(String text);
}

