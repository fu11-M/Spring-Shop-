package com.apple.shop.Sales;

import com.apple.shop.CustomUser;
import com.apple.shop.member.Member;
import com.apple.shop.member.MemberRepository;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class SalesController {
    private final SalesRepository salesRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/order")
    String postOrder(@RequestParam String title,
                     @RequestParam Integer price,
                     @RequestParam Integer count,
                     Authentication auth) {
        Sales sales = new Sales();
        sales.setCount(count);
        sales.setPrice(price);
        sales.setItemName(title);
        CustomUser customUser = (CustomUser) auth.getPrincipal();
        System.out.println(customUser.id);

        var member = new Member();
        member.setId(customUser.id);
        sales.setMember(member);
        salesRepository.save(sales);
        return "redirect:/list";
    }

//    @GetMapping("/order/all")
//    String getOrderAll(Model model) {
////        List<Sales> result = salesRepository.findAll();
////        System.out.println(result.get(0));
////        return "list.html";
////        ----------------------------------------------------
//        List<Sales> result = salesRepository.customUserFindAll();
//        System.out.println(result);
//        var salesDto = new SalesDto();
//        salesDto.itemName = result.get(0).getItemName();
//        salesDto.price = result.get(0).getPrice();
//        salesDto.username = result.get(0).getMember().getUsername();
//        model.addAttribute("salesDto", salesDto);
//        return "sales.html";
//    }
@GetMapping("/order/all")
String getOrderAll(Model model) {
    // salesRepository에서 모든 Sales 데이터를 조회
    List<Sales> result = salesRepository.customUserFindAll();
    System.out.println(result);

    // SalesDto 리스트 생성
    List<SalesDto> salesDtoList = new ArrayList<>();

    // result 리스트에서 모든 Sales 객체를 순차적으로 가져와서 SalesDto로 변환
    for (Sales sales : result) {
        SalesDto salesDto = new SalesDto(sales);
        salesDto.itemName = sales.getItemName();
        salesDto.price = sales.getPrice();
        salesDto.username = sales.getMember().getUsername();
        salesDto.created = String.valueOf(sales.getCreated());
        salesDtoList.add(salesDto);  // 변환된 SalesDto를 리스트에 추가
    }

    // 모델에 SalesDto 리스트 추가
    model.addAttribute("salesDtoList", salesDtoList);
    return "sales.html";  // 템플릿 파일 경로 반환
}

//        -----------------------------------------------------
//        var result = memberRepository.findById(1);
//        System.out.println(result);
//        return "sales.html";
//    }
}

class SalesDto {
    public String itemName;
    public Integer price;
    public String username;
    public String created;

    public SalesDto(Sales sales){
        this.created = sales.getCreated().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 a h시 mm분"));
    }
}