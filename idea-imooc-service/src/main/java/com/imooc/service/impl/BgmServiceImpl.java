package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.pojo.Bgm;
import com.imooc.service.BgmService;
import com.imooc.utils.PagedResult;
import mapper.BgmMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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



    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult queryBgmList(Integer page, Integer PAGE_SIZE) {

        PageHelper.startPage(page,PAGE_SIZE);

        List<Bgm> bgmList = bgmMapper.selectAll();



        PageInfo<Bgm> pageList = new PageInfo<>(bgmList);


        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());
        pagedResult.setRows(bgmList);
        pagedResult.setTotal(pageList.getPages());



        return pagedResult;

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Bgm queryBgmById(String BgmId) {
//        Example BgmExample = new Example(Bgm.class);
//        Example.Criteria criteria = BgmExample.createCriteria();
//        criteria.andEqualTo("id",BgmId);
//        Bgm result = bgmMapper.selectOneByExample(BgmExample);
//        return result;

        return bgmMapper.selectByPrimaryKey(BgmId);

    }

}
