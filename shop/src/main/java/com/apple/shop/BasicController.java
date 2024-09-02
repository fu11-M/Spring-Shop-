//package com.apple.shop;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import java.time.LocalDateTime;
//
//@Controller
//public class BasicController {
//
//    @GetMapping("/")
//    String hello(){
//        return "index.html";
//    }
//
//    @GetMapping("/about")
//    @ResponseBody
//    String about(){
//        return "쇼핑몰 사이트입니다.";
//    }
//
//    @GetMapping("/MyPage")
//    @ResponseBody
//    String MyPage(){
//        return "MyPage";
//    }
//
//    @GetMapping("/date")
//    @ResponseBody
//    String date(){
//        return LocalDateTime.now().toString();
//    }
//}
