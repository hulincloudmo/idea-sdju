package com.imooc.service;

import com.imooc.pojo.MyUsers;

public interface UserService {
    /**
     * fetch data by rule id
     *
     * @param  username rule id
     *
     * @return Result<XxxxDO>
     */
    public boolean queryUsernameIsExist(String username);

    public void saveUser(MyUsers users);

    //用户登录
    public MyUsers queryUserForLogin(String username, String password);

    /**用户修改信息*/
    public void updateUserInfo(MyUsers user);
}
