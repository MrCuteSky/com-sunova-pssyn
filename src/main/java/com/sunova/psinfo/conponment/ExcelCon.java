package com.sunova.psinfo.conponment;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ExcelCon<T> {
    @Value("${ExcelPath}")
    private String excelPath;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    //反射获取实体类属性
    public List<String> getHead(Object o){
        List<String> list = new ArrayList<>();
        Class<?> obj = o.getClass();
        Field[] f = obj.getDeclaredFields();
        for(Field field : f){
            field.setAccessible(true);
            list.add(field.getName());
        }
        return list;
    }

    //泛型兼容
    public String GenExcel(List<T> list,String source) throws Exception{
        List<String> head = getHead(list.get(0));
        Workbook wb = new HSSFWorkbook();
        int rowSize = 0;
        Sheet sheet = wb.createSheet();
        Row row = sheet.createRow(rowSize++);
        OutputStream outputStream = null;
        String name = "";
        SimpleDateFormat dfTime = new SimpleDateFormat("yyyy_MM_dd_HH_MM_SS");
        String filename = source + "_" + dfTime.format(new Date()) + ".xls";
        for (int i = 0; i < head.size(); i++) {
            row.createCell(i).setCellValue(head.get(i));
        }
        while(rowSize <= list.size()){
            T t = list.get(rowSize-1);
            Row newrow = sheet.createRow(rowSize++);
            for(int j = 0;j < head.size();j++){
                Field f = t.getClass().getDeclaredField(head.get(j));
                f.setAccessible(true);
                Object o = f.get(t);
//                String getMethod = "get" + head.get(j).substring(0, 1).toUpperCase() + head.get(j).substring(1);
//                Object o = t.getClass().getMethod(getMethod).invoke(t);
//                name = t.getClass().getName();
                String value = "";
                if(o!=null){
                    value = o.toString();
                }
                newrow.createCell(j).setCellValue(value);
            }
        }
        outputStream = new FileOutputStream(excelPath + filename);
        wb.write(outputStream);
        logger.info("*****"+ excelPath + filename +"生成完成*****");
        if (outputStream != null) {
            outputStream.flush();
            outputStream.close();
        }
        return filename;
    }
}
