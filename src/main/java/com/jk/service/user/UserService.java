package com.jk.service.user;


import com.jk.entity.user.User;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public interface UserService {


    List<User> selectUserList();

    void addBook(User user);

    void deleteUser(User user);
}
