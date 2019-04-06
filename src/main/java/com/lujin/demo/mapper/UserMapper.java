package com.lujin.demo.mapper;

import com.lujin.demo.pojo.User;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {
    @Select("select count(*) from user where username =#{username}")
    int selectUserByUsername(String username);
}
