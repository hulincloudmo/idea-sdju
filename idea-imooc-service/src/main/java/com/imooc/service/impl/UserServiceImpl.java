package com.imooc.service.impl;

import com.imooc.pojo.MyUsers;
import com.imooc.service.UserService;
import mapper.MyUsersMapper;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
/**
 * @author hulincloud
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private MyUsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public boolean queryUsernameIsExist(String username) {

        MyUsers user = new MyUsers();
        user.setUsername(username);
        MyUsers result = usersMapper.selectOne(user);
        return result == null ? false:true;
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public void saveUser(MyUsers user) {
        String userId = sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public MyUsers queryUserForLogin(String username, String password) {

        Example userExample = new Example(MyUsers.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        MyUsers result = usersMapper.selectOneByExample(userExample);

        return result;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    @Override
    public MyUsers queryUserInfo(String userId) {

        Example userExample = new Example(MyUsers.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        MyUsers users = usersMapper.selectOneByExample(userExample);
        return users;

    }
    @Transactional(propagation = Propagation.SUPPORTS,rollbackFor = Exception.class)
    @Override
    public void updateUserInfo(MyUsers user) {
        Example userExample = new Example(MyUsers.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersMapper.updateByExampleSelective(user, userExample);
    }

}
