package com.lujin.demo.controller;

import com.lujin.demo.pojo.PageResult;
import com.lujin.demo.pojo.Patient;
import com.lujin.demo.pojo.Record;
import com.lujin.demo.pojo.User;
import com.lujin.demo.service.PatientService;
import com.lujin.demo.service.RecordService;
import com.lujin.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping()
public class RecordController {
    @Autowired
    RecordService recordService;
    @Autowired
    PatientService patientService;
    @RequestMapping("queryRecord")
    public String QueryRecordByPatient(
        ModelMap model,
        @RequestParam(value = "pid",required = true)   Integer pid,
        @RequestParam(value = "time",defaultValue = "")  String time
    ){
        System.out.println(pid);
        List<Record> records = recordService.queryRecordByPatient(pid);

        model.addAttribute("records",records);
        System.out.println(records.toString());

        return "record";
    }
    @RequestMapping("findRecord")
    public String QueryRecordByPatient(
            ModelMap model,
            @RequestParam(value = "id",required = true)   Integer id){
            model.addAttribute("recordPatient",recordService.QueryRecordPatientbyRid(id));
            return "recordSpecification";
    }
    @RequestMapping("showRecordView")
    private String queryPatinet(ModelMap model,
                                @RequestParam(value="pid",required = true) Integer pid ){

        Patient patient = patientService.queryPatientRecordByPid(pid);
        model.addAttribute("patient",patient);
        return "recordAdd";

    }
    @RequestMapping("addRecord")
    public String  addRecord(
            ModelMap model, @ModelAttribute(value = "record") Record record){
            recordService.addRecord(record);


            return "home";
        }
    @RequestMapping("queryRecordAll")
    private String queryRecord(ModelMap model, String condition,
                                @RequestParam(value="page",defaultValue = "1")Integer page ){
        PageResult<Record> result=this.recordService.selectAllRecord(page);

        result.setCurrentPage( page);
        result.getItems().forEach(x->System.out.println(x));
        model.addAttribute("result" , result);
//        this.recordService.createHtml(result, page);
        return "record";

    }

}
