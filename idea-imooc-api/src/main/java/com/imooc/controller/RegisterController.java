package com.imooc.controller;

import com.imooc.pojo.MyUsers;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
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


		user.setPassword("");

		UsersVO userVO = setUserRedisSessionToken(user);

		return IMoocJSONResult.ok();
	}

	public UsersVO setUserRedisSessionToken(MyUsers userModel) {
		String uniqueToken = UUID.randomUUID().toString();
		redis.set(USER_REDIS_SESSION + ":"+userModel.getId(), uniqueToken,1000*60*30);

		UsersVO usersVO = new UsersVO();
		BeanUtils.copyProperties(userModel, usersVO);
		usersVO.setUserToken(uniqueToken);
		return usersVO;
	}

	@ApiOperation(value="用户注销", notes="用户注销的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true,
			dataType="String", paramType="query")
	@PostMapping("/logout")
	public IMoocJSONResult logout(String userId) {
		redis.del(USER_REDIS_SESSION + ":" +userId);
		return IMoocJSONResult.ok();
	}

}
