package com.lujin.demo.service;

import com.lujin.demo.bean.UserInfo;
import com.lujin.demo.codeUtils.CodecUtils;
import com.lujin.demo.mapper.UserMapper;
import com.lujin.demo.pojo.User;
;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import com.lujin.*;
import sun.security.pkcs11.wrapper.CK_X9_42_DH1_DERIVE_PARAMS;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;
    public int ckeckuser(User user) {
        int count=0;
        if(StringUtils.isBlank(user.getUsername()))
            return 0;
        User user1= new User();
        user1.setUsername(user.getUsername());
        User record=new User();
        record=userMapper.selectOne(user1);
        if (!record.getPassword().equals(CodecUtils.md5Hex(user.getPassword(), record.getSalt()))) {
            return 0;

        }

        else return 1;
    }

    public boolean ckeckHaveNotuser(String username) {
        int num=userMapper.selectUserByUsername(username);
       if(num==0)
           return true;
       else    return false;
    }

    public void register(User user) {

        String salt = CodecUtils.generateSalt();
        String pwd = CodecUtils.md5Hex(user.getPassword(),salt);
        user.setPassword(pwd);
        user.setSalt(salt);
//        user.setCreated(new Date());
        System.out.println(user.toString());
        userMapper.insert(user);

    }
}
