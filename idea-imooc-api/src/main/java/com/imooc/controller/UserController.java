package com.imooc.controller;

import com.imooc.pojo.MyUsers;
import com.imooc.pojo.vo.UsersVO;
import com.imooc.service.UserService;
import com.imooc.utils.HulincloudJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author hulincloud
 */
@RestController
@Api(value = "用户相关业务接口", tags = {"用户操作controller"})
@RequestMapping("/user")
public class UserController extends BasicController {

	@Autowired
	private UserService userService;

	@ApiOperation(value="用户上传头像", notes="用户上传头像的接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "file", value = "头像", required = true, dataType = "MultipartFile", allowMultiple = true),
			@ApiImplicitParam(name="userId", value="用户id", required=true,
					dataType="String", paramType="query")
	})

	@PostMapping(value = "/uploadFace")
	public HulincloudJSONResult uploadFace(@RequestParam(value = "userId",required = true) String userId,
										   @RequestParam(value = "file", required = true) MultipartFile[] files) throws Exception {

		if (StringUtils.isBlank(userId) || userId.equals("undefined")) {
			return HulincloudJSONResult.errorMsg("用户id不能为空...");
		}
		// 文件保存的命名空间
		String fileSpace = "D:\\SDJU_research_userData";
		// 保存到数据库中的相对路径
		String uploadPathDB =   "/" + userId + "/face";

		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;
		try {
			if (files != null && files.length > 0) {

				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
					// 设置数据库保存的路径
					uploadPathDB += ("/" + fileName);

					File outFile = new File(finalFacePath);
					if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						// 创建父文件夹
						outFile.getParentFile().mkdirs();
					}

					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);
				}

			} else {
				return HulincloudJSONResult.errorMsg("上传出错...");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return HulincloudJSONResult.errorMsg("上传出错...");
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
		MyUsers user = new MyUsers();
		user.setId(userId);
		user.setFaceImage(SERVERURL + uploadPathDB);

		userService.updateUserInfo(user);

		return HulincloudJSONResult.ok(user);
	}
	@ApiOperation(value="查询用户信息", notes="查询用户信息的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true,
			dataType="String", paramType="query")
	@PostMapping("/query")
	public HulincloudJSONResult query(String userId) throws Exception {

		if (StringUtils.isBlank(userId)) {
			return HulincloudJSONResult.errorMsg("用户id不能为空...");
		}
		MyUsers userInfo = userService.queryUserInfo(userId);
		UsersVO usersVO = new UsersVO();
		BeanUtils.copyProperties(userInfo, usersVO);
		return HulincloudJSONResult.ok(usersVO);
	}
}
