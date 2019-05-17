package com.imooc.controller;

import com.imooc.service.BgmService;
import com.imooc.utils.IMoocJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hulincloud
 */
@RestController
@RequestMapping("/bgm")
public class BgmController {

	@Autowired
	private BgmService bgmService;
	
	@RequestMapping("/list")
	public IMoocJSONResult list() {

		return IMoocJSONResult.ok(bgmService.queryBgmList());
	}
	
}
