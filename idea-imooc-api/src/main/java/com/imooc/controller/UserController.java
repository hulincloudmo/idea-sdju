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
@Api(value = "用户相关业务接口", tags = {"用户操作controller"})
public class UserController extends BasicController {

	@Autowired
	private UserService userService;

	@ApiOperation(value = "用户注册接口",notes = "注册")
	@PostMapping("/register")
	public IMoocJSONResult register(@RequestBody MyUsers user) {

		return IMoocJSONResult.ok();
}
