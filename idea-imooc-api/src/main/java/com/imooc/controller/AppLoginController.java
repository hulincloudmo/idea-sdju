package com.imooc.controller;

import com.imooc.pojo.BO.MPWXUserBO;
import com.imooc.pojo.MyUsers;
import com.imooc.service.UserService;
import com.imooc.utils.HulincloudJSONResult;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hulincloud
 */
@Api(value = "app微信登录接口", tags = {"app登录controller"})
@RestController
public class AppLoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisOperator redisOperator;

	@ApiOperation(value = "微信登录", notes = "根据code进行用户的登录与注册", httpMethod = "POST")
	@PostMapping("wxforlogin{appopenid}")
	public HulincloudJSONResult WXforAppLogin(
			@ApiParam(name = "appopenid", value = "通过wx.login获得的code",required = true)
			@PathVariable String appopenid,
			@RequestBody MPWXUserBO wxUserBO) {

		boolean appopenidIsExist = userService.queryappopenidIsExist(appopenid);

		if (appopenidIsExist){

			MyUsers userResult = new MyUsers();

		}

		return HulincloudJSONResult.ok();
	}
	
}
