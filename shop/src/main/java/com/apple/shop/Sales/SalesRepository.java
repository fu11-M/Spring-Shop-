package com.apple.shop.Sales;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SalesRepository extends JpaRepository<Sales, Integer> {

//    @Query(value = "qwerasdf" , nativeQuery = true)
//    jpql 문법
    @Query(value = "SELECT sales FROM Sales sales JOIN FETCH sales.member")
    List<Sales> customUserFindAll();
}
