package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.movie;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/movies")
@RestController
public class HomeController {

	private static final String STD_IN = "stdin";
	private static final String STD_ERR = "stderr";
	
	@GetMapping("/recommend")
	public List<movie> mainView(HttpServletRequest request, Model model) throws InterruptedException {
		List<movie> movieList = new ArrayList<>();
		
		String result = runningProcess(); //파이썬 프로그램 실행
		
		String[] tmpArr = result.split("\n");
		System.out.println(tmpArr[0]);
		for(int k=0; k<tmpArr.length; k++) {
			String[] items = tmpArr[k].split(",");

				movie movie = new movie();
				
				movie.setTitle(items[0].trim());
				movie.setVote_count(Integer.parseInt(items[1].replace(" ", "")));
				movie.setVote_average(Double.parseDouble(items[2].replace(" ", "")));
				movie.setScore(Double.parseDouble(items[3].replace(" ", "")));
//				movie.setPost_path(items[4].trim());
				
				movieList.add(movie);
			}

		
		return movieList;
	}
	
	private static String runningProcess() {
		Process process = null;
		File workingDirectory = new File("E:/kmc/cherish-test1/08data/MachineLearning");
		String cmd = "python E:/kmc/cherish-test1/08data/MachineLearning/recommend.py";
		ProcessStream processInStream = null;
		ProcessStream processErrStream = null;
		String result = "";
		
		try {
			// 스레드실행
			process = Runtime.getRuntime().exec(cmd, null, workingDirectory);
			processInStream = new ProcessStream(STD_IN, process.getInputStream());
			processErrStream = new ProcessStream(STD_ERR, process.getErrorStream());
			
			result = processInStream.start();
			processErrStream.start();
			process.getOutputStream().close();
			
			process.waitFor();
		    
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
		
		return processInStream.getResult();
	}
}
