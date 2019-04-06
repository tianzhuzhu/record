package com.lujin.demo.pojo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@Table(name="patient")
public class Patient {
    @Id
    @KeySql(useGeneratedKeys = true)
    private  Integer pid;

    @NotEmpty(message="用户名不能为空")
    @Length(min = 2, max = 32, message = "姓名长度在2到10个字符之间")
    private String name;

    private String sex;

    @Min(value = 0,message = "最小0岁")
    @Max(value = 120,message = "最大120岁")
    private  Integer age;

    @Length(min = 2, max = 32, message = "电话号码长度在11到32个字符之间")
    private String phone;

    @NotEmpty(message="生日不能为空")
    private String birthday;

    @NotEmpty(message="地址不能为空")
    private  String address;

    @Length(min = 15, max = 18, message = "身份证号码在15到18位")
    private  String idcard;
    private  String url;

    private List<Record> records;
}
