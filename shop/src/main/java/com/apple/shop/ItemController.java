package com.apple.shop;

import com.apple.shop.comment.Comment;
import com.apple.shop.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
//@RequiredArgsConstructor //생성자를 자동으로 생성해주는 어노테이션
public class ItemController {
    //원하는 클래스에 repository를 등록한다.
    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final AnnouncementRepository announcementRepository;
    private final S3Service s3Service;
    private final CommentRepository commentRepository;

    // @RequiredArgsConstructor를 사용하지 않는 경우, 스프링은 @Autowired 어노테이션을 통해 의존성 주입을 관리한다.
    // @Autowired 어노테이션은 스프링이 자동으로 ItemRepository 타입의 적절한 빈(bean)을 찾아서 ItemController에 주입한다.
    // 이를 통해 ItemController는 ItemRepository의 구현체가 어떻게 변하든 그 변경에 영향을 받지 않고 안정적으로 기능을 수행할 수 있다.
    // 만약 @Autowired 어노테이션이 없으면, ItemRepository의 구현체에 대한 변경이 ItemController에 직접적인 영향을 미칠 수 있다.
    @Autowired
    public ItemController(ItemRepository itemRepository,
                          ItemService itemService,
                          AnnouncementRepository announcementRepository,
                          S3Service s3Service,
                          CommentRepository commentRepository
    ) {
        this.itemRepository = itemRepository;
        this.itemService = itemService;
        this.announcementRepository = announcementRepository;
        this.s3Service = s3Service;
        this.commentRepository = commentRepository;
    }

    @GetMapping("/list")
    String list(Model model) {
        //Item테이블의 모든 데이터를 가지고온다. 이러한 형식으로 데이터베이스 입출력 쿼리문 없이 사용할 수 있다.
        //itemRepository.findAll() 메소드가 반환하는 타입은 데이터베이스의 항목을 반환하는데 보통 List와 Set의 컬렉션 타입을 반환한다.
        // ListArray나 LinkedList를 타입으로 가져와도 되지만 List로 가져오는 경우는 나중에 두 타입중 선택하여 바꿀 수 있기 때문이다.
        List<Item> itemResult = itemRepository.findAll();
        //System.out.println(result);
        //[com.apple.shop.Item@7ecd8a63, com.apple.shop.Item@3bad5f05, com.apple.shop.Item@3cd05a13] Item클래스의 Object

        //attributeName자리에는 하드 코딩 보단 데이터 베이스의 데이터를 위치시켜주는 것이 좋다.
        model.addAttribute("items", itemResult);

        List<Announcement> announcementsResult = announcementRepository.findAll();
        model.addAttribute("announcements", announcementsResult);
        return "list.html";
    }

    @GetMapping("/write")
    String write() {
        return "write.html";
    }

//     함수 하나에는 한가지 기능만 담는걸 권장하며 아래의 코드와 같이 여러개의 기능이 있으면 분리하는 것이 좋다.
//        @PostMapping("/add")
//    String addPost(@RequestParam(name = "title") String title, @RequestParam(name = "price") Integer price){
//        Item item = new Item(title, price);
//        item.setTitle(title); //DB 입출력 기능
//        item.setPrice(price); //DB 입출력 기능
//        itemRepository.save(item);
//        return "redirect:/list"; // html 보내는 기능
//    }
//}
//
//    @PostMapping("/add")
//    String addPost(@ModelAttribute Item item) { //<input> 데이터들을 바로 Object로 변환하려면 @ModelAttribute를 사용
//        itemRepository.save(item);
//        return "redirect:/list";
//    }

    //다른 Class의 함수를 사용할 때 new Class().함수()는 좋은 관습은 아니다.
    //그 이유는 Post요청이 올 때 마다 객체를 생성하면 너무 많은 객체가 생성되기 때문에 비효율 적이다.
    //하여 다른 곳에서 미리 new Class()하고 재사용 하는 것이 좋은 관습이다.

    @PostMapping("/add")
    String addPost(String title, Integer price, @RequestParam String imageUrl) {
//        new ItemService().saveItem(String title, Integer price);
        System.out.println("imageUrl" + imageUrl);
        itemService.saveItem(title, price, imageUrl);
        return "redirect:/list"; // html 보내는 기능
    }

    //  URL 파라미터 문법을 사용하여 비슷한 URL의 API를 여러개 만들 필요가 없다.
    @GetMapping("/detail/{id}")
    String detail(@PathVariable Integer id, Model model) {
        //  try문법안에 있는 코드들이 에러가 발생한다면 catch문의 코드를 실행한다는 의미이다.
//        try {
//            // Optional타입을 사용해야 하는 이유는 findById 메서드 결과를 Optional로 반환되어 있게 설계되어 있기 때문이다.
//            // 결과가 있을 수도 없을 수도 있어 데이터를 못찾을 수 있기 때문에 Optional를 사용한다.
//            Optional<Item> result = itemRepository.findById(id);
//            //result 변수로 값을 가져 오려면 result에 .get을 사용해야 한다.
//            //하지만 .get()을 사용하면 서버에 위함하다 그 이유는 값이 없을 수 있기 때문에 .get()을 사용하면 값을 가져오지 못해 에러가 난다.
//            //System.out.println(result.get());
//
//            if (result.isPresent()) {
//                model.addAttribute("data", result.get());
//                return "detail.html";
//            } else {
//                return "redirect:/list";
//            }
//        } catch (Exception exception) {
//            System.out.println(exception.getMessage());
//            return "redirect:/list";
//        }
        try {
            Optional<Item> result = itemRepository.findById(id);

            if (result.isPresent()) {
                Item item = result.get();
                model.addAttribute("data", item);

                // 댓글 목록을 추가로 가져와서 모델에 추가
                List<Comment> comments = commentRepository.findAllByParentId(id);
                model.addAttribute("comments", comments);

                return "detail.html";
            } else {
                return "redirect:/list";
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return "redirect:/list";
        }
    }

//        -------------------------------------------------------------------------------------------
//        try {
//            // 1. 먼저 Item을 조회
//            Optional<Item> itemResult = itemRepository.findById(id);
//
//            if (itemResult.isPresent()) {
//                // Item을 모델에 추가
//                Item item = itemResult.get();
//                model.addAttribute("itemData", item);
//
//                // 2. 해당 Item과 관련된 댓글(Comment)을 조회하여 모델에 추가
//                List<Comment> comments = commentRepository.findAllByParentId(item.getId());
//                model.addAttribute("comments", comments);
//
//                return "detail.html";
//            } else {
//                // Item이 존재하지 않으면 리스트 페이지로 리다이렉트
//                return "redirect:/list";
//            }
//        } catch (Exception exception) {
//            // 예외가 발생하면 리스트 페이지로 리다이렉트
//            System.out.println(exception.getMessage());
//            return "redirect:/list";
//        }
//}

    //모든 API의 에러를 캐치하려면 @ExceptionHandler를 사용하면 된다.
    //class안에 있는 모든 API에서 Exception 발생시 안의 코드를 실행해준다.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler(){
        return ResponseEntity.status(400).body("userError");
    }

    @GetMapping("/edit/{id}")
    String edit(Model model, @PathVariable Integer id){
        Optional<Item> result =  itemRepository.findById(id);
        if(result.isPresent()){
            model.addAttribute("data", result.get());
            return "edit.html";
        }else{
            return "redirect:/list";
        }
    }

    @PostMapping("/edit")
    String editItem(@RequestParam Integer id, @RequestParam String title, @RequestParam Integer price){
        Item item = new Item();
        item.setId(id); // id가 1인 행이 없으면 내용을 새로운 내용으로 추가해주고 id가 1인 행이 있으면 아래의 내용으로 덮어씌어 준다.
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
        return "redirect:/list";
    }

    @DeleteMapping("/item")
    //query String으로 보낸 데이터 출력은 @RequestParam을 사용한다.
    ResponseEntity<String> deleteItem(@RequestParam Integer id){
        itemRepository.deleteById(id);
        //AJAX를 사용하면 redirect문법은 쓸 수 없다.
        return ResponseEntity.status(200).body("삭제완료");
    }
    
//    //bcrypt로 해싱
//    @GetMapping("/test2")
//    String deleteItem(){
//        var result = new BCryptPasswordEncoder().encode("문자"); //endcode를 사용하면 함수안에 있는 데이터를 해싱해준다.
//        System.out.println(result);
//        return "redirect:/list";
//    }

    @GetMapping("/list/page/{pagenation}")
    String getListPage(Model model, @PathVariable Integer pagenation){
        //일부 테이블을 가져오는 함수
        //slice
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(pagenation - 1, 12));// (몇번째 페이지, 페이지당 몇개)
        model.addAttribute("items", result);
        System.out.println(result.getTotalPages());
        System.out.println(result.hasNext());
        return "list.html";
    }

    @PostMapping("/search")
    String postSearch(Model model, @RequestParam String searchText){
//        var result = itemRepository.findAllByTitleContains(searchText);
        var result = itemRepository.rawQuery1(searchText);
        System.out.println(result);
        model.addAttribute("items", result);
        return "searchResult.html";
    }
    // 검색 우선 수위는 기본적으로 구현되어 있음

    @GetMapping("/presigned-url")
    @ResponseBody
    String getURL(@RequestParam String filename){
        var result = s3Service.createPreSignedUrl("test/" + filename);
        System.out.println(filename);
        return result;
    }
}




