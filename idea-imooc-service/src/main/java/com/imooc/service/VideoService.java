package com.imooc.service;

import com.imooc.pojo.Videos;
import com.imooc.utils.PagedResult;

import java.util.List;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.service
 * @ClassName: VideoService
 * @Author: hulincloud
 * @Description: 视频处理
 * @Date: 2019/5/23 9:58
 * @Version: 1.0
 */
public interface  VideoService {
    public PagedResult getAllVideos(
            Videos video,Integer SaveRecord , Integer page, Integer pagesize);

    public String saveVideo(Videos videos);

    public void updateVideo(String videoId, String coverPath);

//    public String updateVideo(String videoId, String coverpath)

    public List<String> getHotwords();
/***
 *
 * @DESCRIPTON: 用户是否喜欢Video
 * @param userId
 * @param videoId
 * @param videoCreaterId
 * @return void
 * @author hulincloud
 * @date 2019/6/6 21:25
 */
    public void userLikeVideo(String userId, String videoId, String videoCreaterId);

    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);
}

