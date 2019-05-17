package com.imooc.service.impl;

import com.imooc.pojo.Bgm;
import com.imooc.service.BgmService;
import mapper.BgmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: idea-imooc
 * @Package: com.imooc.service.Impl
 * @ClassName: BgmServiceImpl
 * @Author: hulincloud
 * @Description:
 * @Date: 2019/5/17 10:56
 * @Version: 1.0
 */
@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }
}
