package com.imooc.controller;

import com.imooc.pojo.Bgm;
import com.imooc.pojo.Videos;
import com.imooc.service.BgmService;
import com.imooc.service.UserService;
import com.imooc.service.VideoService;
import com.imooc.utils.HulincloudJSONResult;
import com.imooc.utils.MergeVideoMp3;
import com.imooc.utils.PagedResult;
import enums.VideoStatusEnum;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author hulincloud
 */
@RestController
@Api(value = "视频相关接口", tags = {"视频controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {

	@Autowired
	private BgmService bgmService;

	@Autowired
	private UserService userService;

	@Autowired
	private VideoService videoService;

	@ApiOperation(value="用户上传视频", notes="用户上传视频的接口")
	@ApiImplicitParams({
			@ApiImplicitParam(name="userId", value="用户id", required=true,
					dataType="String", paramType="form"),
			@ApiImplicitParam(name="bgmId", value="背景音乐id", required=false,
					dataType="String", paramType="form"),
			@ApiImplicitParam(name="videoSeconds", value="背景音乐播放长度", required=true,
					dataType="String", paramType="form"),
			@ApiImplicitParam(name="videoWidth", value="视频宽度", required=true,
					dataType="String", paramType="form"),
			@ApiImplicitParam(name="videoHeight", value="视频高度", required=true,
					dataType="String", paramType="form"),
			@ApiImplicitParam(name="desc", value="视频描述", required=false,
					dataType="String", paramType="form")
	})
	@PostMapping(value="/upload", headers="content-type=multipart/form-data")
	public HulincloudJSONResult upload(String userId,
									   String bgmId, double videoSeconds,
									   int videoWidth, int videoHeight,
									   String desc,
									   @ApiParam(value="短视频", required=true)
												   MultipartFile file) throws Exception {

		if (StringUtils.isBlank(userId)) {
			return HulincloudJSONResult.errorMsg("用户id不能为空...");
		}



		// 文件保存的命名空间
//		String fileSpace = "D:\\SDJU_research_userData";
		// 保存到数据库中的相对路径
		String uploadPathDB = "/" + userId + "/video";

		FileOutputStream fileOutputStream = null;
		InputStream inputStream = null;

		String finalVideoPath = "";

		try {
			if (file != null ) {

				String fileName = file.getOriginalFilename();
				if (StringUtils.isNotBlank(fileName)) {
					// 文件上传的最终保存路径
					finalVideoPath = FILE_SPACE + uploadPathDB + "/" + fileName;
					// 设置数据库保存的路径
					uploadPathDB += ("/" + fileName);

					File outFile = new File(finalVideoPath);
					if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
						// 创建父文件夹
						outFile.getParentFile().mkdirs();
					}

					fileOutputStream = new FileOutputStream(outFile);
					inputStream = file.getInputStream();
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

		//判断BGMID是否为空，如果BGM不为空，就查询BGM信息，并且合并视频，生成新的视频
		if (StringUtils.isNotBlank(bgmId)) {
			Bgm bgm = bgmService.queryBgmById(bgmId);
			String mp3InputPath =FILE_SPACE + bgm.getPath();
			MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
			String videoInputPath = finalVideoPath;
			String videoOutPutName = UUID.randomUUID().toString()+ ".mp4";
			uploadPathDB = "/" + userId + "/video" + "/" + videoOutPutName;
			finalVideoPath = FILE_SPACE + uploadPathDB;

			tool.convertor(videoInputPath, mp3InputPath, videoSeconds, finalVideoPath);
			System.out.println(uploadPathDB);
		}

		//保存数据库
		Videos video = new Videos();
		video.setAudioId(bgmId);
		video.setUserId(userId);
		video.setVideoSeconds((float)videoSeconds);
		video.setVideoWidth(videoWidth);
		video.setVideoHeight(videoHeight);
		video.setVideoDesc(desc);
		video.setVideoPath(finalVideoPath);
		video.setStatus(VideoStatusEnum.SUCCESS.value);
		video.setCreateTime(new Date());
		videoService.saveVideo(video);
		return HulincloudJSONResult.ok();
	}
	@ApiOperation(value = "使用BgmId查询BGM状态", notes="查询BGM的接口")
	@GetMapping("/queryByBgmId")
	public HulincloudJSONResult test(String bgmid){
		Bgm bgm = bgmService.queryBgmById(bgmid);
		return HulincloudJSONResult.ok(bgm);
	}

	@PostMapping("/showAll")
	public HulincloudJSONResult showAll(Integer page){

		if (page == null){
			page = 1;
		}

		PagedResult result = videoService.getAllVideos(page, PAGE_SIZE);
		return HulincloudJSONResult.ok(result);
	}

}
