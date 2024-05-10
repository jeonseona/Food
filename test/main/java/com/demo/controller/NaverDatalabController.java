//package com.demo.controller;
//
//import org.apache.tomcat.util.json.JSONParser;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.demo.service.NaverBlogSearchApi;
//
//import lombok.RequiredArgsConstructor;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/open")
//public class NaverDatalabController{
//	@Autowired
//	NaverBlogSearchApi naver = new NaverBlogSearchApi();
//	
//	@ResponseBody
//	@GetMapping("naver/blog")
//	public ResponseEntity<JSONObject> getPlace(@RequestParam("query") String query) throws Exception {
//		JSONParser parser = new JSONParser();
//		Object obj = parser.parse( naver.search(query) );
//		JSONObject jsonObj = (JSONObject) obj;
//		return ResponseEntity.ok(jsonObj);
//	}
//}