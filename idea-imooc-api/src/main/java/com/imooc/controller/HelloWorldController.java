package com.imooc.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hulincloud
 */
@RestController

public class HelloWorldController {


	
	@PostMapping("/basic")
	public String basic() {
		return "这是复制用的";
	}
	
}
