package com.lujin.demo.mapper;

import com.lujin.demo.pojo.Patient;
import com.lujin.demo.pojo.User;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import javax.persistence.FetchType;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

public interface PatientMapper extends Mapper<Patient> {
    @Select("SELECT * FROM patient where pid =#{pid}")
    @Results(value = {
            @Result(id = true, column = "pid", property = "pid"),
            @Result(column = "pid", property = "records",
                    many = @Many(
                            select = "com.lujin.demo.mapper.RecordMapper.selectRecordByPid"
                    )
            )
    })

    Patient querryPatientRecordByPid(Integer pid);

//    @Select("SELECT * FROM patient p RIGHT JOIN  record r ON p.pid = r.pid where r.rid=#{rid}")
//    Patient slectOnePatientByRid(Integer rid);
//    @Select("SELECT * FROM patient p LEFT JOIN  record r ON p.pid = r.pid where p.pid=#{pid}")
//    Patient slectPatientByPid(Integer pid);

}
