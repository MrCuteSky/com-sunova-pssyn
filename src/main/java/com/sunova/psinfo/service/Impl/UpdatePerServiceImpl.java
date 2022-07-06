package com.sunova.psinfo.service.Impl;

import com.sunova.psinfo.entities.updateEntities.DingTalkUp;
import com.sunova.psinfo.entities.updateEntities.WeChatUp;
import com.sunova.psinfo.mapper.EmployeeDtMapper;
import com.sunova.psinfo.mapper.EmployeeWcMapper;
import com.sunova.psinfo.service.UpdatePerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UpdatePerServiceImpl implements UpdatePerService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    EmployeeDtMapper employeeDtMapper;
    @Autowired
    EmployeeWcMapper employeeWcMapper;
    @Override
    public void UpdateDingTalk() {
        List<DingTalkUp> list = employeeDtMapper.getNewInsertInfo();
//        System.out.println(list);
    }

    @Override
    public void UpdateWeChat() {
        List<WeChatUp> list = employeeWcMapper.getNewInsertInfo();
    }

}
