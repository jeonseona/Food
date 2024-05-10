package com.demo.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
	
	//회원별 장바구니 상품 목록
	//JPQL 
	// 여기서 Cart는 table이 아니라 Cart class 지칭
	@Query("SELECT c FROM Cart c WHERE c.member.id = ?1 AND c.result='1'")
	public List<Cart> getCartList(String userId);
}
