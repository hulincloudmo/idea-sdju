package com.imooc.controller;

import com.imooc.pojo.MyUsers;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;

import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hulincloud
 */
@RestController
@Api(value = "用户登录接口", tags = {"注册登录controller"})
public class RegisterController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户注册接口",notes = "注册")
	@PostMapping("/register")
	public IMoocJSONResult register(@RequestBody MyUsers user) {

		/**
		 * 1.判断用户名密码不为空
		 * **/
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
			return IMoocJSONResult.errorMsg("用户名和密码不能为空");
		}


		boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());

		if (!usernameIsExist){
			try {
				user.setNickname(user.getUsername());
				user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
				user.setFansCounts(0);
				user.setReceiveLikeCounts(0);
				user.setFollowCounts(0);
				userService.saveUser(user);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}else {
			return IMoocJSONResult.errorMsg("用户已经存在");
		}






		return IMoocJSONResult.ok();
	}
	
}
