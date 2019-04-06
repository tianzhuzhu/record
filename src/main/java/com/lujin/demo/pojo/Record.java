package com.lujin.demo.pojo;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Data
@Table(name="record")
public class Record {
    @Id
    @KeySql(useGeneratedKeys = true)
    Integer rid;
    Integer pid;
    String time;
    String description;

    Patient patient;

}
