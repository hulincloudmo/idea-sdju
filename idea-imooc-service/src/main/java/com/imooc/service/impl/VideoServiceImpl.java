package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.pojo.SearchRecords;
import com.imooc.pojo.UsersLikeVideos;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.VideosVO;
import com.imooc.service.VideoService;
import com.imooc.utils.PagedResult;
import mapper.*;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

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
    private Sid sid;



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

            PagedResult pagedResult = new PagedResult();

        return pagedResult;
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
}
