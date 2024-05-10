package com.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.demo.domain.Cart;
import com.demo.domain.Member;
import com.demo.domain.OrderDetail;
import com.demo.domain.Orders;
import com.demo.domain.Product;
import com.demo.dto.OrderVO;
import com.demo.service.CartService;
import com.demo.service.OrderService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MypageController {

	@Autowired
	private CartService cartService;

	@Autowired
	private OrderService orderservice;

	@PostMapping("/cart_insert")
	public String insertCart(@RequestParam("pseq") int pseq, Cart vo, HttpSession session) {
		String url = "";

		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) { // 로그인 안되어있음.

			url = "member/login"; // 로그인 화면으로 이동
		} else {
			Product p = new Product();
			p.setPseq(pseq);

			Member m = new Member();
			m.setId(loginUser.getId());

			vo.setMember(m);
			vo.setProduct(p);

			cartService.insertCart(vo);

			url = "redirect:cart_list";
		}
		return url;
	}

	@GetMapping("/cart_list")
	public String cartList(HttpSession session, Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) { // 로그인 안되어있음.

			return "member/login"; // 로그인 화면으로 이동
		} else {
			List<Cart> cartList = cartService.getCartList(loginUser.getId());

			// 장바구니 총액 계산
			int totalAmount = 0;
			for (Cart vo : cartList) {
				totalAmount += vo.getQuantity() * vo.getProduct().getPrice2();
			}
			model.addAttribute("cartList", cartList);
			model.addAttribute("totalPrice", totalAmount);
		}
		return "mypage/cartList";
	}

	@PostMapping("cart_delete")
	public String deleteCart(@RequestParam(value = "cseq") int[] cseq) {

		// 장바구니 삭제
		for (int i : cseq) {
			cartService.deleteCart(i);
		}

		return "redirect:cart_list";
	}

	@PostMapping("/order_insert")
	public String orderInsert(HttpSession session, Orders order, RedirectAttributes model) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "member/login";
		} else {
			// orders 객체에 사용자 정보 설정
			order.setMember(loginUser);

			int oseq = orderservice.insertOrder(order);
			System.out.println("oseq 확인 : " + oseq);
			model.addAttribute("oseq", oseq);
			return "redirect:order_list";
		}
	}

	/*
	 * 장바구니에서 주문한 내역 표시
	 */
	@GetMapping("/order_list")
	public String order_list(HttpSession session, @RequestParam(value = "oseq") int oseq, Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {

			return "member/login"; // 로그인 화면으로 이동

		} else {
			// 주문내역 조회
			Orders order = orderservice.getListOrderById(loginUser.getId(), oseq);

			// 주문 상세내역 조회
			// List<OrderDetail> orderlist =
			// orderservice.getListOrderDetailById(loginUser.getId(), oseq);

			// 주문 총액 계산
			int totalAmount = 0;
			for (OrderDetail vo : order.getOrderDetailList()) {
				totalAmount += vo.getQuantity() * vo.getProduct().getPrice2();
			}

			model.addAttribute("orderList", order.getOrderDetailList());
			model.addAttribute("orderDate", order.getIndate());
			model.addAttribute("totalPrice", totalAmount);

			return "mypage/orderList";
		}

	}

	// 진행중인 사용자 주문내역 요약조회
	@GetMapping("/mypage")
	public String myPageView(HttpSession session, Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");

		if (loginUser == null) {
			return "member/login";

		} else {
			// (1) 사용자의 진행중인 주문번호 목록조회
			List<Integer> list = orderservice.getSeqOrdering(loginUser.getId(), "1");

			// (2) 각 주문번호에 대해 주문조회 및 요약정보 생성
			List<OrderVO> summaryList = new ArrayList<>();
			for (int oseq : list) {
				OrderVO summary = new OrderVO();

				// 주문번호별 주문조회
				Orders order = orderservice.getListOrderById(loginUser.getId(), oseq);

				// 각 주문의 요약정보 생성
				summary.setOseq(order.getOseq());
				summary.setIndate(order.getIndate());
				// 한 주문번호의 상품 건수
				int detailSize = order.getOrderDetailList().size();

				// 상세 주문목록에서 첫번째 항목의 상품명 저장
				String summaryPname = order.getOrderDetailList().get(0).getProduct().getName();
				if (detailSize > 1) {
					summary.setPname(summaryPname + " 외" + (detailSize - 1) + " 건");
				} else {
					summary.setPname(summaryPname);
				}
				// 각 주문별 합계 금액
				int amount = 0;
				for (int i = 0; i < detailSize; i++) {
					amount += order.getOrderDetailList().get(i).getQuantity()
							* order.getOrderDetailList().get(i).getProduct().getPrice2();
				}
				summary.setPrice2(amount);
				summaryList.add(summary);
			}

			// (3) 주문정보를 화면에 전달
			model.addAttribute("title", "My page(진행중인 주문내역)");
			model.addAttribute("orderList", summaryList);

		}
		return "mypage/mypage";
	}
	
	
	@GetMapping("/order_detail")
	public String orderDetailView(Orders vo, HttpSession session, Model model) {

		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {

			return "member/login"; // 로그인 화면으로 이동

		} else {
			// 주문내역 조회
			Orders order = orderservice.getListOrderById(loginUser.getId(), vo.getOseq());

			// 주문 총액 계산
			int totalAmount = 0;
			for (OrderDetail detail : order.getOrderDetailList()) {
				totalAmount += detail.getQuantity() * detail.getProduct().getPrice2();
			}
			// 화면에 출력할 정보
			model.addAttribute("title", "My Page(주문 상세 정보)");
			model.addAttribute("order", order);
			model.addAttribute("totalPrice", totalAmount);

			return "mypage/orderDetail";
		}

	}

	// 총 주문내역
	@GetMapping("order_all")
	public String getOrderAllView(HttpSession session, Model model) {
		Member loginUser = (Member) session.getAttribute("loginUser");
		if (loginUser == null) {
			return "member/login";
		} else { // 주문내역 조회
			List<Integer> list = orderservice.getSeqOrdering(loginUser.getId(), "");
			List<OrderVO> summaryList = new ArrayList<>();
			for (int oseq : list) {
				OrderVO summary = new OrderVO();
				Orders order = orderservice.getListOrderById(loginUser.getId(), oseq);

				// 각 주문의 요약정보 생성
				summary.setOseq(order.getOseq());
				summary.setIndate(order.getIndate());
				// 한 주문번호의 상품 건수
				int detailSize = order.getOrderDetailList().size();
				// 상세 주문목록에서 첫번째 항목의 상품명 저장
				String summaryPname = order.getOrderDetailList().get(0).getProduct().getName();
				if (detailSize > 1) {
					summary.setPname(summaryPname + " 외" + (detailSize - 1) + " 건");
				} else {
					summary.setPname(summaryPname);}

				// 주문 총액 계산
				int Amount = 0;
				for (OrderDetail detail : order.getOrderDetailList()) {
					Amount += detail.getQuantity() * detail.getProduct().getPrice2();	}
				summary.setPrice2(Amount);
				summaryList.add(summary);

				// 화면에 출력할 정보
				model.addAttribute("title", "My Page(총 주문내역)");
				model.addAttribute("orderList", summaryList);
			}
			return "mypage/mypage";
		}

	}
}
