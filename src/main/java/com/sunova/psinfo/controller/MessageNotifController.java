package com.sunova.psinfo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sunova.psinfo.conponment.DingCallbackCrypto;
import com.sunova.psinfo.entities.Attendance;
import com.sunova.psinfo.mapper.AttendenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class MessageNotifController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AttendenceMapper attendenceMapper;

    private final String TOKEN = "agwyuicbcUDaHX1nR4";

    private final String AES_KEY = "lxQbLEFITMXStJeftHHjGMkgocijaLWc5rJRJNiIuOA";

    private final String APP_KEY = "dingdrkpfra6xlucvtl2";

    @PostMapping("/dingTalkCallBack")
    public Map<String, String> dingTalkCallBack (
            @RequestParam(value = "msg_signature", required = false) String msg_signature,
            @RequestParam(value = "timestamp", required = false) String timeStamp,
            @RequestParam(value = "nonce", required = false) String nonce,
            @RequestBody(required = false) JSONObject json) {

        try {
            // 1. 从http请求中获取加解密参数
            // 2. 使用加解密类型
            // Constant.OWNER_KEY 说明：
            // 1、开发者后台配置的订阅事件为应用级事件推送，此时OWNER_KEY为应用的APP_KEY。
            // 2、调用订阅事件接口订阅的事件为企业级事件推送，
            //      此时OWNER_KEY为：企业的appkey（企业内部应用）或SUITE_KEY（三方应用）
            DingCallbackCrypto callbackCrypto = new DingCallbackCrypto(TOKEN, AES_KEY, APP_KEY);
            String encryptMsg = json.getString("encrypt");
            String decryptMsg = callbackCrypto.getDecryptMsg(msg_signature, timeStamp, nonce, encryptMsg);

            // 3. 反序列化回调事件json数据
            JSONObject eventJson = JSON.parseObject(decryptMsg);
            String eventType = eventJson.getString("EventType");

            // 4. 根据EventType分类处理
            if ("check_url".equals(eventType)) {
                // 测试回调url的正确性
                logger.info("*****测试回调url的正确性*****");

            } else if ("meetingroom_book".equals(eventType)) {
                // 会议预约信息推送
                logger.info("会议预约推送：" + eventJson.getString("eventSubType") + "\n" + eventJson.toJSONString());
//                System.out.println(eventJson.toJSONString());
            } else if("attendance_check_record".equals(eventType)){
                //考勤打卡信息推送
                JSONArray jsonArray = JSONArray.parseArray(eventJson.getString("DataList"));
                Attendance attendance = JSONObject.parseObject(jsonArray.get(0).toString(),Attendance.class);
                attendenceMapper.insertAttendenceInfo(attendance);
            } else{
                // 其他事件暂不处理
//                logger.info("*****发生了：" + eventType + "事件*****");
            }

            // 5. 返回success的加密数据
            Map<String, String> successMap = callbackCrypto.getEncryptedMap("success");
//            logger.info(successMap.toString());
            return successMap;

        } catch (Exception e) {
            logger.error("*****回调函数执行出错！*****");
            e.printStackTrace();
        }
        return null;
    }

}
