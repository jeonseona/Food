package com.demo.persistence;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.domain.Cart;
import com.demo.domain.Member;
import com.demo.domain.Product;

import jakarta.transaction.Transactional;

@SpringBootTest
public class CartRepositoryTest {
	
	@Autowired
	private CartRepository cartRepo;
	@Autowired
	private CartRepository memberRepo;
	@Autowired
	private CartRepository productRepo;
	
	@Disabled
	@Test
	@Transactional
	public void testInsertCart() {
		Member member1 = 
				new Member("one", "1111", "홍길동", "one@email.com", "", "", "","", new Date());
		
		Product product = new Product("크로커다일부츠", "2", 40000, 50000, 10000, "오리지날 크로커다일부츠 입니다.", "w2.jpg", "n", "y", new Date());
		
		Cart cart = Cart.builder().member(member1).product(product)
				.quantity(1).build();
		
		cartRepo.save(cart);}
		
		
		@Disabled
		@Test
		public void testSelectCart() {
			Optional<Cart> item = cartRepo.findById(1);
			
			Cart cart = item.get();
			
			System.out.println(cart);
			System.out.println("장바구니 사용자명: " + cart.getMember().getName());
			System.out.println("장바구니 제품명 : " + cart.getProduct().getName());
		}
		
		@Disabled
		@Test
		public void testCartList() {
		List<Cart>	list = cartRepo.getCartList("one");
		
		for(Cart cart : list) {
			System.out.println(cart);
		}
			
		}
		
		
		
		
	}
	
	
