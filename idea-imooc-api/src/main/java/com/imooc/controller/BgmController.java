package com.imooc.controller;

import com.imooc.service.BgmService;
import com.imooc.utils.HulincloudJSONResult;
import com.imooc.utils.PagedResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hulincloud
 */
@RestController
@Api(value = "背景音乐接口", tags = "背景音乐controller")
@RequestMapping("/bgm")
public class BgmController extends BasicController {

	@Autowired
	private BgmService bgmService;

	@ApiOperation(value = "获取音乐列表", notes = "获取背景音乐列表接口")
	@GetMapping("/list")
	public HulincloudJSONResult list(Integer page) {

		if (page == null){
			page = 1;
		}

		PagedResult pagedResult = bgmService.queryBgmList(page, PAGE_SIZE);


		return HulincloudJSONResult.ok(pagedResult);
	}
	
}
