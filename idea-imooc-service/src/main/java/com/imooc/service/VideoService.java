package com.imooc.service;

import com.imooc.pojo.Videos;
import com.imooc.utils.PagedResult;

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
    public PagedResult getAllVideos(Integer page, Integer pagesize);

    public String saveVideo(Videos videos);

    public void updateVideo(String videoId, String coverPath);

//    public String updateVideo(String videoId, String coverpath)
}

