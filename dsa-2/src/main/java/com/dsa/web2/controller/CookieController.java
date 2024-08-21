package com.dsa.web2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("cookie")
public class CookieController {

	//쿠키 저장
	@GetMapping("cookie1")
	public String cookie1(HttpServletResponse response) {
		// 쿠키 객체로부터 객체 생성
		Cookie c1 = new Cookie("str","abcde");
		Cookie c2 = new Cookie("num","12345");
		
		// 인스턴스.setMaxAge(수명(초단위));
		c1.setMaxAge(60*60*24*3);
		c2.setMaxAge(60*60*24*3);
		
		// response 객체에 쿠키 저장
		response.addCookie(c1);
		response.addCookie(c2);
		
		return "redirect:/";
	}
	
	// 쿠키 삭제
	@GetMapping("cookie2")
	public String cookie2(HttpServletResponse response) {
		// 쿠키 객체로부터 객체 생성
		Cookie c1 = new Cookie("str","abcde");
		Cookie c2 = new Cookie("num","12345");
		
		// 쿠키 수명 (수명시간을 0으로 설정해서 삭제)
		c1.setMaxAge(0);
		c2.setMaxAge(0);
		
		// response 객체에 쿠키 저장
		response.addCookie(c1);
		response.addCookie(c2);
		
		return "redirect:/";
	}
	
	// 쿠키 정보 읽기
	@GetMapping("cookie3")
	public String cookie3(
			@CookieValue(name="str", defaultValue="없음") String str,
			@CookieValue(name="num", defaultValue="0") int num
			) {
		log.debug("cookie값 확인 > str:{}, num:{}",str,num);
		return "redirect:/";
	}
}
