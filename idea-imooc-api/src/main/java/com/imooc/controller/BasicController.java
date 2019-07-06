package com.imooc.controller;

import com.imooc.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hulincloud
 */
@RestController
public class BasicController {


	
	@Autowired
	public RedisOperator redis;

	public static final String USER_REDIS_SESSION = "user-redis-session";

	public static final String FFMPEG_EXE = "D:\\SDJU_research_userData\\ffmpeg\\bin\\ffmpeg.exe";

	public static final String SERVERURL = "http://192.168.2.233:8081";

	/***
	 *
	 *

	 * 文件保存路径
	 * @author hulincloud
	 * @date 2019/5/21 12:04
	 */
	public static final String FILE_SPACE = "D:\\SDJU_research_userData";

	public static final Integer PAGE_SIZE  = 4;
}
