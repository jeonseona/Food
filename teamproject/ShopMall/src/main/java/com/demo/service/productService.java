package com.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.domain.Product;

import jakarta.transaction.Transactional;

public interface productService {
	
	public void insertProduct(Product vo);
	
	public List<Product> getNewProductList();
	
	public List<Product> getBestProductList();
	
	public Product getProduct(int pseq);
	
	public List<Product> getProductListByKind(String kind);
	
	//이름을 조건으로 전체 상품 조회
	public List<Product> getAllProducts(String name);
	
	//페이지별 전체 상품 조회
	// page - 조회할 페이지 번호
	// size - 페이지당 게시글의 수
	public Page<Product> getAllProductsByName(String name, int page, int size);
	
	public void updateProduct(Product vo);
	
}
