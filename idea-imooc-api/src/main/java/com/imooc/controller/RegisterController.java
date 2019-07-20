package com.imooc.controller;

import com.imooc.pojo.MyUsers;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.HulincloudJSONResult;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author hulincloud
 */
@RestController
@Api(value = "用户登录接口", tags = {"注册登录controller"})
public class RegisterController extends BasicController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户注册接口",notes = "注册")
	@PostMapping("/register")
	public HulincloudJSONResult register(@RequestBody MyUsers user) {

		/**
		 * 1.判断用户名密码不为空
		 * **/
		if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
			return HulincloudJSONResult.errorMsg("用户名和密码不能为空");
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
			return HulincloudJSONResult.errorMsg("用户已经存在");
		}




		UsersVO userVO = setUserRedisSessionToken(user);

		return HulincloudJSONResult.ok(userVO);
	}


	@ApiOperation(value="用户登录", notes="用户登录的接口")
	@PostMapping("/userlogin")
	public HulincloudJSONResult login(@RequestBody MyUsers user) throws Exception {
		String username = user.getUsername();
		String password = user.getPassword();

//		Thread.sleep(3000);

		// 1. 判断用户名和密码必须不为空
		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			return HulincloudJSONResult.errorMsg("用户名或密码不能为空...");
		}

		// 2. 判断用户是否存在
		MyUsers userResult = userService.queryUserForLogin(username,
				MD5Utils.getMD5Str(password));

		// 3. 返回

		if (userResult != null) {
			userResult.setPassword("");
			UsersVO userVO = setUserRedisSessionToken(userResult);
			return HulincloudJSONResult.ok(userVO);
		} else {
			return HulincloudJSONResult.errorMsg("用户名或密码不正确, 请重试...");
		}
	}

	public UsersVO setUserRedisSessionToken(MyUsers userModel) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION + ":"+userModel.getId(), uniqueToken,USERKEEPALIVE);

		UsersVO usersVO = new UsersVO();
		BeanUtils.copyProperties(userModel, usersVO);
		usersVO.setUserToken(uniqueToken);
		return usersVO;
	}

	@ApiOperation(value="用户注销", notes="用户注销的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true,
			dataType="String", paramType="query")
	@PostMapping("/logout")
	public HulincloudJSONResult logout(String userId) {
		redis.del(USER_REDIS_SESSION + ":" +userId);
		return HulincloudJSONResult.ok();
	}

}
