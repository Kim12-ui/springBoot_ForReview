package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping({"","/"})
	public String Index() {
		return "Index";
	}
	
	@GetMapping("path111")
	public String image() {
		return "image";
	}
}
