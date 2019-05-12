package com.imooc.controller;

import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author hulincloud
 */
@RestController
@Api(value = "用户相关业务接口", tags = {"用户操作controller"})
public class UserController extends BasicController {

	@Autowired
	private UserService userService;

	@ApiOperation(value="用户上传头像", notes="用户上传的接口")
	@ApiImplicitParam(name="userId", value="用户id", required=true,
			dataType="String", paramType="query")
	@PostMapping("/uploadFace")
	public IMoocJSONResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {
//			保存路径
			String fileSpace = "D:\\SDJU_research_userData";

			String uploadPathDB = "/" + userId +"/face";

			FileOutputStream fileOutputStream = null;
			InputStream inputStream = null;

		try {
			if (files != null && files.length>0){

				String fileName = files[0].getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;

					uploadPathDB +=("/" + fileName);
					File outFile = new File(finalFacePath);
					if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()){

						outFile.getParentFile().mkdir();

					}

					fileOutputStream = new FileOutputStream(outFile);
					inputStream = files[0].getInputStream();
					IOUtils.copy(inputStream, fileOutputStream);

				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}

		return IMoocJSONResult.ok();
	}
}
