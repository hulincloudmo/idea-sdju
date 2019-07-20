package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentsVO;
import com.imooc.pojo.vo.VideosVO;
import com.imooc.service.VideoService;
import com.imooc.utils.PagedResult;
import com.imooc.utils.TimeAgoUtils;
import mapper.*;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @ProjectName: Sdju-Reasearch
 * @Package: com.service
 * @ClassName: VideoServiceImpl
 * @Author: hulincloud
 * @Description:
 * @Date: 2019/5/23 9:59
 * @Version: 1.0
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private MyUsersMapper myUsersMapper;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UsersReportMapper usersReportMapper;

    @Autowired
    private Sid sid;

    @Autowired
    private CommentsMapper commentsMapper;

    @Autowired
    private  CommentsMapperCustom commentsMapperCustom;



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public String saveVideo(Videos videos) {

        String id = sid.nextShort();
        videos.setId(id);
        videosMapper.insertSelective(videos);
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public PagedResult getAllVideos(Integer page, Integer pageSize) {
        PageHelper.startPage(page,pageSize);

        List<VideosVO> list = videosMapperCustom.queryAllVideo();


        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);

        return pagedResult;
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public PagedResult searchVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {

        String desc = video.getVideoDesc();

        if (isSaveRecord != null && isSaveRecord == 1){
            SearchRecords record = new SearchRecords();

            String descId = sid.nextShort();
            record.setId(descId);
            record.setContent(desc);

            searchRecordsMapper.insert(record);
        }
            PageHelper.startPage(page, pageSize);
            List<VideosVO> list = videosMapperCustom.searchAllVideo(desc);
            PageInfo<VideosVO> pageList = new PageInfo<>(list);

            PagedResult pagedResult = new PagedResult();
            pagedResult.setPage(page);
            pagedResult.setRecords(pageList.getTotal());
            pagedResult.setTotal(pageList.getPages());
            pagedResult.setRows(list);

        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public List<String> getHotWord() {

        return searchRecordsMapper.getHotWords();
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void userLikeVideo(String userId, String videoId, String videoCreatorId) {

        String likeId = sid.nextShort();

        UsersLikeVideos usersLikeVideos = new UsersLikeVideos();
        usersLikeVideos.setId(likeId);
        usersLikeVideos.setUserId(userId);
        usersLikeVideos.setVideoId(videoId);

        usersLikeVideosMapper.insert(usersLikeVideos);

        videosMapperCustom.addVideoLikeCount(videoId);

        myUsersMapper.addReceiveLikeCount(userId);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void userUnLikeVideo(String userId, String videoId, String videoCreatorId) {

        Example example = new Example(UsersLikeVideos.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);

        usersLikeVideosMapper.deleteByExample(example);

        videosMapperCustom.reduceVideoLikeCount(videoId);

        myUsersMapper.reduceReceiveLikeCount(userId);





    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    @Override
    public void saveComment(Comments comments) {
        String commentId = sid.nextShort();
        comments.setId(commentId);
        comments.setCreateTime(new Date());
        commentsMapper.insert(comments);
    }

    @Override
    public void report(UsersReport usersReport) {
        String reportId = sid.nextShort();
        usersReport.setId(reportId);
        usersReport.setCreateDate(new Date());
        usersReportMapper.insert(usersReport);
    }

    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {



        PageHelper.startPage(page, pageSize);

        List<CommentsVO> list = commentsMapperCustom.queryComments(videoId);

        for (CommentsVO commentsVO : list){
            String timeAgo = TimeAgoUtils.format(commentsVO.getCreateTime());
            commentsVO.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVO> pageList = new PageInfo<>(list);

        PagedResult result = new PagedResult();
        result.setPage(page);
        result.setRecords(pageList.getTotal());
        result.setRows(list);
        result.setTotal(pageList.getPages());

        return result;
    }


}
