package com.example.util;


import com.example.entity.StudentEntity;
import com.example.enums.DateEnum;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author sjl
 */
public class TestDataUtil {
    public static List<StudentEntity> getData() throws ParseException {
        List<StudentEntity> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            StudentEntity studentEntity = new StudentEntity();
            String id = i+UUID.randomUUID().toString().replace("-", "");
            String name="张三"+i;
            Date date = new Date();
            studentEntity.setAge(i+10);
            studentEntity.setClazz("计算机网络技术");
            studentEntity.setDate(date);
            studentEntity.setId(id);
            studentEntity.setName(name);
            list.add(studentEntity);
        }
        list.add(new StudentEntity("UUID_TEST"+UUID.randomUUID().toString().replace("-", ""), "错误的李四", 18, "会计", DateUtil.string2Date("2016-12-12", DateEnum.YYYY_MM_DD.getFormat())));
        list.add(new StudentEntity("UUID_TEST_TEST"+UUID.randomUUID().toString().replace("-", ""), "错误的李四TEST", 18, "机电", DateUtil.string2Date("2016-11-11", DateEnum.YYYY_MM_DD.getFormat())));
        return list;
    }
}
