package com.lujin.demo.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


@Data
@Table(name="user")
public class User {
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;
    @Length(min = 2, max = 32, message = "姓名长度在2到32个字符之间")
    private String username;
    @Length(min = 2, max = 64, message = "密码长度在2到32个字符之间")
    private String password;
    private String tellphone;
    private String email;
    private  String salt;
    private Date created;
    private String personalright;







}
