package com.jk.service.user;

import com.jk.entity.user.User;
import com.jk.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
@Service
public class UserServiceImpl implements UserService{



    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> selectUserList() {

        return userMapper.selectUserList();
    }

    @Override
    public void addBook(User user) {
        userMapper.addBook(user);

    }

    @Override
    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }
}
