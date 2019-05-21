package com.imooc.controller;

import com.imooc.utils.HulincloudJSONResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hulincloud
 */
@RestController
public class HelloWorldController {
	@RequestMapping(value="/hello",method= RequestMethod.GET)
	public HulincloudJSONResult basic() {
		return HulincloudJSONResult.ok("hello");
	}
	
}
