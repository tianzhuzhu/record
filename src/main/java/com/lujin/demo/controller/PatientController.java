package com.lujin.demo.controller;

import com.lujin.demo.pojo.PageResult;
import com.lujin.demo.pojo.Patient;
import com.lujin.demo.pojo.User;
import com.lujin.demo.service.PatientService;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.ibatis.annotations.Select;
import org.apache.logging.log4j.message.ReusableMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;
    private static final List<String> suffixes = Arrays.asList("image/png", "image/jpeg");
//    private final ResourceLoader resourceLoader;

    @RequestMapping("addPatient")

    public String  addPatient(
            ModelMap model, @Valid @ModelAttribute(value = "patient") Patient patient, BindingResult result,@RequestParam (value="image") MultipartFile img){

        try {
//            System.out.println( img.getBytes());

            String type=img.getContentType();
            if (suffixes.contains(type)) {
                BufferedImage image = ImageIO.read(img.getInputStream());

                if (image!=null)
                {
                    String url= "D:\\1\\";
//                    File dir = new File(url);
//                    URL ul=new URL(url);
                    File dir=new File(url);

                    img.transferTo(new File(dir, patient.getPid()+img.getOriginalFilename()));
                    patient.setUrl("1"+"\\"+img.getOriginalFilename());
                }

                System.out.println(patient.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        if(result.hasErrors())
            return "patientManagement";
        Integer count = patientService.addPatient(patient);
        if(count>0)
            return "home";
        else {
            if (count == 0)
                model.addAttribute("message", "您输入的身份信息重复");
            else
                model.addAttribute("message", "添加失败");
            return "home";
        }

    }

    @RequestMapping("queryPatient")
    private String queryPatient(ModelMap model, @RequestParam(value="condition",defaultValue = "")String condition,
                                @RequestParam(value="page",defaultValue = "1")Integer page ){
        PageResult<Patient> result=this.patientService.queryPatientBypageAndCondition(condition,page);
        result.setCurrentPage( page);
        model.addAttribute("result" , result);

        return "patientManagement";

    }
    @RequestMapping("queryPatientById")
    private String queryPatientById(ModelMap model,
                                @RequestParam(value="pid",required = true)Integer pid ){
        Patient patient = patientService.queryPatientById(pid);
        model.addAttribute("oddPatient",patient);

        return "patientModify";

    }
    @RequestMapping("modifyPatient")
    public String  addPatient(
            ModelMap model,@ModelAttribute(value = "patient") Patient patient){
        this.patientService.modifyPatient(patient);
        return "home";

    }
    @RequestMapping("showPatient")
//    @ResponseBody
    public ResponseEntity showPatient(String url){
//        return ResponseEntity.ok(resourceLoader.getResource("file"+url));
        return null;
    }

}
