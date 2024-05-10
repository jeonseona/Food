package com.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.demo.domain.Product;
import com.demo.service.productService;

@Controller
public class ProductController {
	
	@Autowired
	productService productService;
	
	@GetMapping("/product_detail")
	public String getProductDetailView(Product vo, Model model) {
		//상세정보 조회
		Product product = productService.getProduct(vo.getPseq());
		model.addAttribute("productVO" , product);
		return "product/productDetail";
	}
	
	@GetMapping("/category")
	public String productKindAction(Product vo, Model model) {
		List<Product> kindlist = productService.getProductListByKind(vo.getKind());
		
		model.addAttribute("productKindList", kindlist);
		
		return "product/productKind";
	}

}
