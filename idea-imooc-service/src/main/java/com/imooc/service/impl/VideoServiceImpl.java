package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.pojo.Videos;
import com.imooc.pojo.vo.VideosVO;
import com.imooc.service.VideoService;
import com.imooc.utils.PagedResult;
import mapper.VideosMapper;
import mapper.VideosMapperCustom;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    private Sid sid;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public PagedResult getAllVideos(Integer page, Integer pageSize) {

        PageHelper.startPage(page,pageSize);

        List<VideosVO> list = videosMapperCustom.queryAllVideos();

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }
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
    public void updateVideo(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }
}
