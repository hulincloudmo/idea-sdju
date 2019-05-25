package com.imooc.controller;

import com.imooc.pojo.BO.MPWXUserBO;
import com.imooc.pojo.BO.WXSessionBO;
import com.imooc.pojo.MyUsers;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.HttpClientUtil;
import com.imooc.utils.HulincloudJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author hulincloud
 */
@Api(value = "微信登录接口", tags = {"app微信登录或小程序微信登录"})
@RestController
public class WXLoginController extends BasicController {

	@Autowired
	private UserService userService;

	@Autowired
	private RedisOperator redisOperator;

	@ApiOperation(value = "微信登录", notes = "根据code进行用户的登录与注册", httpMethod = "POST")
	@PostMapping(value="/mpWXLogin/{code}")
	public HulincloudJSONResult WxLogin(
			@ApiParam(name = "code", value = "通过wx.login获得的code",required = true)
			@PathVariable String code,
//			@ApiParam(name = "workid", value = "前端传入的教师工号",required = true)
//			@PathVariable String workid,
			@RequestBody MPWXUserBO wxUserBO) throws Exception {

		String appid = "wxbbc5d7b8d7205814";
		String secret = "ea74bfcc0146e70e3518c257892f22ca";

		String url = "https://api.weixin.qq.com/sns/jscode2session";
		Map<String , String > param = new HashMap<>();
		param.put("appid", appid);
		param.put("secret",secret);
		param.put("js_code", code);
		param.put("grant_type", "authorization_code");

		String wxResult = HttpClientUtil.doGet(url, param);

		WXSessionBO model = JsonUtils.jsonToPojo(wxResult, WXSessionBO.class);

		String openId = model.getOpenid();
		boolean userIsExist = userService.queryOpenIdIsExist(openId);
		MyUsers userResult = new MyUsers();

		if (userIsExist) {
			//登录
			userResult = userService.queryUserForLoginWX(openId);
			System.out.println("用户存在，登录成功");
		}else {
			//尚未注册，拒绝用户注册
//			boolean workIdIsExist = userService.queryWorkIdIsExist(workid);
//			if (workIdIsExist){
//
//			}else{
//				return HulincloudJSONResult.errorMsg("此工号尚未注册，请联系管理员添加");
//			}
			//开放注册业务
			userResult = userService.saveUserMPWXOpenId(openId,wxUserBO);

		}


		String uniqueToken = UUID.randomUUID().toString().trim();
		redisOperator.set(USER_REDIS_SESSION + ":"+ userResult.getId(),uniqueToken,1000*60*30);

		UsersVO usersVO = new UsersVO();
		BeanUtils.copyProperties(userResult,usersVO);
		usersVO.setUserToken(uniqueToken);

		return HulincloudJSONResult.ok(usersVO);
	}
	
}
