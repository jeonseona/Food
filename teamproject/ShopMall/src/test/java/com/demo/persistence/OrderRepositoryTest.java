package com.demo.persistence;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.demo.domain.Member;
import com.demo.domain.OrderDetail;
import com.demo.domain.Orders;
import com.demo.domain.Product;
import com.demo.dto.SalesCountInterface;
import com.demo.service.OrderDetailRepository;

@SpringBootTest
public class OrderRepositoryTest {
	
	@Autowired
	OrderRepository orderRepo;
	
	@Autowired
	OrderDetailRepository odRepo;
	
	@Disabled
	@Test
	public void testOderInsert() {
		//주문번호 할당
		int oseq = orderRepo.selectMaxOseq();
		Member member = new Member("test", "1111", "test", "test@email.com","","","","y", new Date() );
		Orders order1 = Orders.builder().oseq(oseq)
				.member(member)
				.indate(new Date())
				.build();
		
		//첫번째 주문 데이터
		orderRepo.save(order1);
		
		//두번째 주문 데이터
		oseq = orderRepo.selectMaxOseq();
		Orders order2 = Orders.builder().oseq(oseq)
				.member(member)
				.indate(new Date())
				.build();
		orderRepo.save(order2);
		
		//세번째 주문 데이터
		Member member2 = new Member("test2", "test2", "test2", "test2@email.com","","","","y", new Date() );
		oseq = orderRepo.selectMaxOseq();
		Orders order3 = Orders.builder().oseq(oseq)
				.member(member2)
				.indate(new Date())
				.build();
		orderRepo.save(order3);
	}
	
	@Disabled
	@Test
	public void orderDetailInsert() {
		Member member = new Member("test", "1111", "test", "test@email.com","","","","y", new Date() );
		
		Orders order1 = new Orders(1, member, null, null  );
		Orders order2 = new Orders(2, member, null, null  );
		Orders order3 = new Orders(3, member, null, null  );
		
		Product product1 = new Product(1, null, null, 0,0,0,null, null, null, null, null);
		Product product2 = new Product(2, null, null, 0,0,0,null, null, null, null, null);
		Product product3 = new Product(3, null, null, 0,0,0,null, null, null, null, null);
		Product product4 = new Product(4, null, null, 0,0,0,null, null, null, null, null);
		Product product6 = new Product(6, null, null, 0,0,0,null, null, null, null, null);
		
		OrderDetail[] odArr = {
				new OrderDetail(0, order1, product1, 1, "1"),
				new OrderDetail(0, order1, product2, 2, "1"),
				new OrderDetail(0, order2, product4, 3, "1"),
				new OrderDetail(0, order2, product3, 1, "1"),
				new OrderDetail(0, order3, product2, 1, "1"),
				new OrderDetail(0, order3, product6, 2, "1"),
				new OrderDetail(0, order3, product1, 2, "1")
		};
		for(OrderDetail od : odArr) {
			odRepo.save(od);
		}
	}
	
	@Disabled
	@Test
	public void testGetListOrderById() {
		List<OrderDetail> orderlist  = orderRepo.getListOrderById("test", 1, "1");
		System.out.println("주문상세목록");
		for(OrderDetail od : orderlist) {
			System.out.println(od);
		}
	}
	
	@Disabled
	@Test
	public void testgetOrderByMemberId() {
		Orders order = orderRepo.getOrderByMemberId("test", 1);
		
		System.out.println("<<주문 내역 조회>>");
		System.out.println("주문번호: " + order.getOseq());
		System.out.println("주문일자: " + order.getIndate());
		System.out.println("=================================== ");
		for(OrderDetail od : order.getOrderDetailList()) { 
			System.out.println(od);
		}
	}
	
	@Disabled
	@Test
	public void testgetSeqOrdering() {
		List<Integer> list= orderRepo.getSeqOrdering("test", "1");
		System.out.println("사용자별 주문 번호 목록");
		for(Integer i : list) {
			System.out.println(i);
		}
	}
	
	@Disabled
	@Test
	public void testGetOrderListByName() {
		
		List<OrderDetail> orderList = orderRepo.getOrderListByName("test");
		
		for(OrderDetail od : orderList) {
			System.out.println(od);
		}
	}
	
	
	@Test
		public void testSalesReport() {
		List<SalesCountInterface> list = 
				orderRepo.findSalesCountReport();
		
		for(SalesCountInterface item : list) {
			System.out.printf(" %s : %d\n ", item.getPname(), item.getSales_count());
		}
		
	}
	
}
