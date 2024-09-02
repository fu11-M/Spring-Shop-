//package com.apple.shop;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
//
////모든 Controller 파일의 에러를 캐치하려면 @ControllerAdvice를 사용한다.
//@ControllerAdvice
//public class MyExceptionHandler {
//
//    //restAPI는 의미 없기 때문에 아래의 코드는 지워도 무방함
//    //특정 에러를 종류마다 코드를 작성할 수 있음
//    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
//    public ResponseEntity<String> handler1(){
//        return ResponseEntity.status(400).body("userError");
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<String> handler(){
//        return ResponseEntity.status(400).body("userError");
//    }
//
//}
