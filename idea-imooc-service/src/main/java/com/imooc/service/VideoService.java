package com.imooc.service;

import com.imooc.pojo.Comments;
import com.imooc.pojo.UsersReport;
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

    public String saveVideo(Videos videos);

    public PagedResult getAllVideos(Integer page, Integer pageSize);

    public PagedResult searchVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

    public List<String> getHotWord();

    public void userLikeVideo(String userId, String videoId, String videoCreatorId);

    public void userUnLikeVideo(String userId, String videoId, String videoCreatorId);

    public void saveComment(Comments comments);

    public void report(UsersReport usersReport);

    public PagedResult getAllComments(String videoId, Integer page,Integer pageSize);

}

