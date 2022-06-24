package com.sunova.psinfo.controller;

import com.alibaba.fastjson.JSONArray;
import com.sunova.psinfo.conponment.ExcelCon;
import com.sunova.psinfo.entities.CommonResult;
import com.sunova.psinfo.entities.Dept_Shr;
import com.sunova.psinfo.entities.Employee_Shr;
import com.sunova.psinfo.service.ShrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShrController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ShrService shrService;
    @Autowired
    ExcelCon<Employee_Shr> excelCon_Employee;
    @Autowired
    ExcelCon<Dept_Shr> excelCon_Dept;
    @GetMapping("/shr/empolyee/json/genexcel/{filename}")
    public String empolyee_get_info(@PathVariable("filename") String filename){
        try{
            List<Employee_Shr> list = JSONArray.parseArray(shrService.get_all_per_detail(filename).toString(), Employee_Shr.class);
            excelCon_Employee.GenExcel(list,"person_shr");
            return new CommonResult(200,"员工信息获取成功").toString();
        }catch (Exception e){
            logger.error("*****员工信息获取失败*****",e);
            return new CommonResult(402,e.getMessage()).toString();
        }
    }

    @GetMapping("/shr/dept/json/genexcel/{filename}")
    public String dept_get_info(@PathVariable("filename") String filename){
        try{
            List<Dept_Shr> list = JSONArray.parseArray(shrService.get_dept(filename).toString(), Dept_Shr.class);
            excelCon_Dept.GenExcel(list,"dept_shr");
            return new CommonResult(200,"部门信息获取成功").toString();
        }catch (Exception e){
            logger.error("*****部门信息获取失败*****",e);
            return new CommonResult(402,e.getMessage()).toString();
        }
    }

    @GetMapping("/shr/empolyee/init/database/{filename}")
    public void updateEmpDb(@PathVariable("filename") String filename){
        try {
            shrService.init_Database_Employee_Shr(filename);
        } catch (Exception e) {
            logger.error("*****更新Shr人员信息数据库出错*****",e);
        }
    }

    @GetMapping("/shr/dept/init/database/{filename}")
    public void updateDepDb(@PathVariable("filename") String filename){
        try {
            shrService.init_Database_Dept_Shr(filename);
        } catch (Exception e) {
            logger.error("*****更新Shr部门信息数据库出错*****",e);
        }
    }
}
