package com.sunova.psinfo.conponment;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.stream.Collectors;

@Component
public class JsonCon {
    @Value("${JsonPath}")
    private String jsonPath;
    //读取json文件
    //identify(识别的有用信息)
    //value(比较值)
    //type表示筛选方式 1equals 2contains 3startsWith
    public JSONArray readJsonFile(String fileName, String identify, String value ,int type) throws Exception{
        File jsonFile = new File(jsonPath + fileName);
        JSONArray jsonArray = JSONArray.parseArray(FileUtils.readFileToString(jsonFile,"utf-8"));
        JSONArray result = null;
        if(identify != null){
            switch (type){
                case 1:result = (JSONArray)jsonArray.stream().filter(iter -> ((JSONObject) iter).getString(identify)!=null && ((JSONObject) iter).getString(identify).equals(value)).collect(Collectors.toCollection(JSONArray::new));
                        break;
                case 2:result = (JSONArray)jsonArray.stream().filter(iter -> ((JSONObject) iter).getString(identify)!=null && ((JSONObject) iter).getString(identify).contains(value)).collect(Collectors.toCollection(JSONArray::new));
                        break;
                case 3:result = (JSONArray)jsonArray.stream().filter(iter -> ((JSONObject) iter).getString(identify)!=null && ((JSONObject) iter).getString(identify).startsWith(value)).collect(Collectors.toCollection(JSONArray::new));
                        break;
            }
        }else{
            result = jsonArray;
        }
        return result;
    }

//    public List<Employee_Shr> readJsonFile(String fileName) {
//        List<Employee_Shr> list = new ArrayList<>();
//        boolean complet = true;
//        try {
//            File jsonFile = new File(jsonPath + fileName);
//            FileReader fileReader = new FileReader(jsonFile);
//            Reader reader = new InputStreamReader(new FileInputStream(jsonFile),"utf-8");
//            int ch = 0;
//            StringBuffer sb = new StringBuffer();
//            while ((ch = reader.read()) != -1) {
//                char temp = (char) ch;
//                if(temp == "{".charAt(0)){
//                    complet = false;
//                }else if(temp == "}".charAt(0)){
//                    complet = true;
//                    sb.append(temp);
//                    Employee_Shr employee_shr = JSONObject.parseObject(sb.toString(),Employee_Shr.class);
//                    String org_number = employee_shr.getOrg_number();
//                    if(org_number.startsWith("230000")){
//                        list.add(employee_shr);
//                    }
//                    sb = new StringBuffer();
//                }
//                if(!complet){
//                    sb.append(temp);
//                }
//            }
//            fileReader.close();
//            reader.close();
//            return list;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}
