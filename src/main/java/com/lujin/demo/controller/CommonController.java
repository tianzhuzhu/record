package com.lujin.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class CommonController{
        @RequestMapping("/*")
        public String go(Model model){
            return "home";
        }
        @RequestMapping("/tologin")
        public String tologin(){
            return "login";
        }
        @RequestMapping("/toPatientRegistration")
        public String toPatientRegistration(){
            return "patientRegistration";
        }
        @RequestMapping("/toPatientRecordAdd")
        public  String toPatientRecordAdd(){
            return "toPatientRecordAdd";
        }
        @RequestMapping("/toRegistry")
        public  String toRegisry(){
            return "register";
        }
}
