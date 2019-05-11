package com.imooc.service;

import com.imooc.pojo.MyUsers;

public interface UserService {
    public boolean queryUsernameIsExist(String username);

    public void saveUser(MyUsers users);
}
