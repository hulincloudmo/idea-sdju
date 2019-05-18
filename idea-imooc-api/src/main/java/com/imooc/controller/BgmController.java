package com.imooc.controller;

import com.imooc.service.BgmService;
import com.imooc.utils.HulincloudJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hulincloud
 */
@RestController
@Api(value = "背景音乐接口", tags = "背景音乐controller")
@RequestMapping("/bgm")
public class BgmController {

	@Autowired
	private BgmService bgmService;

	@ApiOperation(value = "获取音乐列表", notes = "获取背景音乐列表接口")
	@PostMapping("/list")
	public HulincloudJSONResult list() {

		return HulincloudJSONResult.ok(bgmService.queryBgmList());
	}
	
}
