package com.imooc.service;

import com.imooc.pojo.MyUsers;

public interface UserService {
    /**
     * fetch data by rule id
     *
     * @param  username rule id
     *
     * @return boolean<用户名是否存在>
     */
    public boolean queryUsernameIsExist(String username);
    /**
     *
     *
     * @param users
     * @return void
     * @author hulincloud
     * @date 2019/5/17 10:47
     */
    public void saveUser(MyUsers users);

    /**
     *
     *
     * @param username 用户对象
     * @param password 密码
     * @return Result<user>
     * @author hulincloud
     * @date 2019/5/17 10:44
     */
    public MyUsers queryUserForLogin(String username, String password);

    /**
     *
     * 用户查询信息
     * @param user 用户对象
     * @return 查询用户信息
     * */
    public MyUsers queryUserInfo(String user);

    /**
     *  用户修改信息
     * @param user  用户对象
     *
     * **/
    public void updateUserInfo(MyUsers user);

}
