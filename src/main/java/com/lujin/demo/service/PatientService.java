package com.lujin.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujin.demo.mapper.PatientMapper;
import com.lujin.demo.pojo.PageResult;
import com.lujin.demo.pojo.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientMapper patientMapper;

    public Integer addPatient(Patient patient) {
        Example example =new Example(Patient.class);
        example.createCriteria().andEqualTo("idcard",patient.getIdcard());
        int count=patientMapper.selectCountByExample(example);
        if (count>0)
        return 0;
        else {
            int result =  patientMapper.insert(patient);
            if(result>0)
                return 1;
            else return -1;
        }

    }

    public PageResult<Patient> queryPatientBypageAndCondition(String condition, Integer page){
        PageHelper.startPage(page,5);
        Example example = new Example(Patient.class);


        if (!StringUtils.isEmpty(condition)){
            try {
                Integer number=Integer.parseInt(condition);
                if(number>1000)
                throw new Exception();
                example.createCriteria().andEqualTo("age",number);

            }
            catch (Exception e){
                if(condition.length()>14)
                example.createCriteria().andEqualTo("idcard",condition);
                else
                example.createCriteria().andLike("name","%"+condition+"%");
            }
        }
        example.setOrderByClause("pid Desc");
        List<Patient> patients = patientMapper.selectByExample(example);
        PageInfo<Patient> patientPageInfo=new PageInfo<>(patients);
        long total = patientPageInfo.getTotal();

        double ceil = Math.ceil((double) total / (10D));
        long totalpage= (long) ceil;
        System.out.println(totalpage);
        return new PageResult<Patient>(total,totalpage,patientPageInfo.getList());
    }
    public Patient queryPatientRecordByPid(Integer pid){
        return  this.patientMapper.querryPatientRecordByPid(pid);
    }

    public Patient queryPatientById(Integer pid) {
        return this.patientMapper.querryPatientRecordByPid(pid);
    }

    public Integer modifyPatient(Patient patient) {

        return this.patientMapper.updateByPrimaryKey(patient);

    }


//    public Patient queryPatientRecordByRid(Integer rid){
//        System.out.println(this.patientMapper.slectPatientByPid(1).toString()+"1!!!!!!!!!!!");
//        return this.patientMapper.slectOnePatientByRid(rid);
//    }
}
