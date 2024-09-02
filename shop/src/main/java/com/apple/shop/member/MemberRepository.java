package com.apple.shop.member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {
   Optional<Member> findByUsername(String username); // Derived query methods
//   Optional<Member> findByDisplatName(String display_Name);
}
