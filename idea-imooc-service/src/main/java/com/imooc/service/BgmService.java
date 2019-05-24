package com.imooc.service;

import com.imooc.pojo.Bgm;

import java.util.List;

/**
 * @author hulincloud
 */
public interface BgmService {
    /**
     *
     *
     * @param
     * @return java.util.List<com.imooc.pojo.Bgm> ss
     * @author hulincloud
     * @date 2019/5/17 11:00
     */
    public List<Bgm> queryBgmList();
    /***
     *
     *
     * @param
     * @return com.imooc.pojo.Bgm
     * @author hulincloud
     * @date 2019/5/20 15:29
     */
    public Bgm queryBgmById(String BgmId);


}
