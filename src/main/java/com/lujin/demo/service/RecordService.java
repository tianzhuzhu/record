package com.lujin.demo.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lujin.demo.mapper.PatientMapper;
import com.lujin.demo.mapper.RecordMapper;
import com.lujin.demo.pojo.PageResult;
import com.lujin.demo.pojo.Patient;
import com.lujin.demo.pojo.Record;
import com.lujin.demo.vo.RecordPatient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import tk.mybatis.mapper.entity.Example;

import java.awt.image.renderable.ContextualRenderedImageFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

@Service
public class RecordService {
    @Autowired
    RecordMapper recordMapper;
    @Autowired
    PatientMapper patientMapper;
    @Autowired
    TemplateEngine templateEngine;
    public List<Record> queryRecordByPatient(Integer pid) {
        Example example = new Example(Patient.class);
        example.createCriteria().andEqualTo("pid",pid);
//        System.out.println(recordMapper.selectAllrecord().toString()+"测试mapper");;
//        return recordMapper.selectByExample(example);
        List<Record> records = recordMapper.selectRecordByPid(pid);
        Patient patient = patientMapper.selectByPrimaryKey(pid);
        records.forEach(x->x.setPatient(patient));
        return  records;

    }

    public RecordPatient QueryRecordPatientbyRid(Integer rid){
        RecordPatient recordPatient = new RecordPatient();
        Record record = new Record();
        record=this.recordMapper.selectByPrimaryKey(rid);
        Patient patient = this.patientMapper.selectByPrimaryKey(record.getPid());
        recordPatient.setPatient(patient);
        recordPatient.setRecord(record);
        return recordPatient;


    }

    public void addRecord(Record record) {

        this.recordMapper.insert(record);
    }

    public PageResult<Record> selectAllRecord(Integer page) {
        PageHelper.startPage(page,5);
        List<Record> records=this.recordMapper.selectAllrecord();
        PageInfo<Record> patientPageInfo=new PageInfo<>(records);
        long total = patientPageInfo.getTotal();
        double ceil = Math.ceil((double) total / (10D));
        long totalpage= (long) ceil;
        System.out.println(totalpage);
        return new PageResult<Record>(total,totalpage,patientPageInfo.getList());

    }
    public void createHtml(PageResult<Record> result,int page){
        PrintWriter printWriter=null;
        try {
        Context context = new Context();
        context.setVariable("result", result);
        File file=new File("D:\\1\\"+"record\\"+page+".html");

            printWriter = new PrintWriter(file);

            templateEngine.process("record", context,printWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            printWriter.close();
        }


    }
}
