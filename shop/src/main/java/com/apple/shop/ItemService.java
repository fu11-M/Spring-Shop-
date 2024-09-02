package com.apple.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    // DB입출력과 같은 기능들을 비즈니스 로직이라고 부르는데 이것을 만드는 Class를 보통 ItemService라고 한다.
    // 기능들을 각각 함수로 뺄 땐 안에 있는 변수들도 따로 정의해야한다.
    // 변수들을 파라미터로 가져와서 선언하면 된다.
    public void saveItem(String title, Integer price, String imageUrl){
        Item item = new Item();
        item.setTitle(title); //DB 입출력 기능
        item.setPrice(price); //DB 입출력 기능
        item.setImageUrl(imageUrl);
        itemRepository.save(item);
    }
}
