package com.lujin.demo.mapper;


import com.lujin.demo.pojo.Record;

import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RecordMapper extends Mapper<Record> {
    @Select("SELECT * FROM record where pid =#{pid}")
    public List<Record> selectRecordByPid(Integer pid);

    @Select("SELECT * FROM record where rid =#{rid}")
    @Results(value = {
            @Result(id = true, column = "pid", property = "pid"),
            @Result(column = "pid", property = "patient",
                    one = @One(
                            select = "com.lujin.demo.mapper.PatientMapper.selectByPrimaryKey"
                    )
            )
    })
     Record selectRecordByRid(Integer rid);


    @Select("SELECT * FROM record")
    @Results(value = {
            @Result(id = true, column = "pid", property = "pid"),
            @Result(column = "pid", property = "patient",
                    one = @One(
                            select = "com.lujin.demo.mapper.PatientMapper.selectByPrimaryKey"
                    )
            )
    })
    List<Record> selectAllrecord();


}
