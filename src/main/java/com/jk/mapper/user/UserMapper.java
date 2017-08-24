package com.jk.mapper.user;

import com.jk.entity.user.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
public interface UserMapper {
    @Select("select userId as \"userID\",userName as \"userName\" from users")
    List<User> selectUserList();

    @Insert("insert into users (userId,userName) values (HIBERNATE_SEQUENCE.nextval,#{userName}) ")
    void addBook(User user);

   @Delete("delete from users where userID = #{userID}")
    void deleteUser(User user);
}
