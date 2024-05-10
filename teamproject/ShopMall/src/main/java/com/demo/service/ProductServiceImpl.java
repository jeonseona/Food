package com.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.demo.domain.Cart;
import com.demo.domain.Product;
import com.demo.persistence.ProductRepository;

@Service
public class ProductServiceImpl implements productService {
	
	@Autowired
	ProductRepository productRepo;

	@Override
	public List<Product> getNewProductList() {
		return productRepo.getnewProduct();
		
	}

	@Override
	public List<Product> getBestProductList() {		
		return productRepo.getbestProduct();
	}

	@Override
	public Product getProduct(int pseq) {
		return productRepo.findById(pseq).get();
	}

	@Override
	public List<Product> getProductListByKind(String kind) {
		return productRepo.findProductByKindContaining(kind);
	}
	/*
	 * 전체 상품 조회
	 */
	@Override
	public List<Product> getAllProducts(String name) {
		return productRepo.findProductByNameContainingOrderByName(name);
	}
	
	/*
	 * page번호는 0부터 시작하므로 -1을 해준다. (내가 가고싶은페이지가 1일경우 0이 될수있도록)
	 */
	@Override
	public Page<Product> getAllProductsByName(String name, int page, int size) {
		Pageable pageable = PageRequest.of(page-1, size, Direction.ASC, "name");

		
		return productRepo.findAllProductsByNameContaining(name, pageable);
	}

	@Override
	public void insertProduct(Product vo) {
		productRepo.save(vo);
		
	}
	
	@Override
	public void updateProduct(Product vo) {
		Product p = productRepo.findById(vo.getPseq()).get();
		
		vo.setRegdate(p.getRegdate()); //등록일은 기존의 데이터 사용
		
		productRepo.save(vo);
		
	}
	
	
	
	
	
}
