package com.advance.mistra.controller.jvm;

import com.advance.mistra.model.jpa.JpaUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * @Author: Mistra
 * @Version: 1.0
 * @Time: 2020/4/19 16:52
 * @Description: JVM内存溢出，CPU飙高，死锁测试controller
 * @Copyright (c) Mistra,All Rights Reserved.
 * @Github: https://github.com/MistraR
 * @CSDN: https://blog.csdn.net/axela30w
 */
@Slf4j
@RestController
@RequestMapping("/jvm")
public class JVMTuningController {

    private List<JpaUser> userList = new ArrayList<JpaUser>();
    private List<Class<?>> classList = new ArrayList<Class<?>>();

    /**
     * 演示堆内存溢出
     * -Xmx64M -Xms64M 设置堆大小
     * 发生内存溢出导出dump文件到当前路径
     * -Xmx64M -Xms64M -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./
     */
    @GetMapping("/heap")
    public String heap() {
        int i = 0;
        while (true) {
            userList.add(new JpaUser(UUID.randomUUID().toString()));
        }
    }

    /**
     * 演示Metaspace区内存溢出
     * -XX:MetaspaceSize=128M -XX:MaxMetaspaceSize=128M
     */
    @GetMapping("/nonheap")
    public String nonheap() {
        while (true) {
            classList.addAll(Metaspace.createClasses());
        }
    }


    /**
     * 演示死循环导致cpu使用率飙高
     * */
    @RequestMapping("/loop")
    public List<Long> loop(){
        String data = "{\"data\":[{\"partnerid\":]";
        return getPartneridsFromJson(data);
    }

    private Object lock1 = new Object();
    private Object lock2 = new Object();

    /**
     * 演示死锁 导致cpu使用率飙高
     * */
    @RequestMapping("/deadlock")
    public String deadlock(){
        new Thread(()->{
            synchronized(lock1) {
                try {Thread.sleep(1000);}catch(Exception e) {}
                synchronized(lock2) {
                    System.out.println("Thread1 over");
                }
            }
        }) .start();
        new Thread(()->{
            synchronized(lock2) {
                try {Thread.sleep(1000);}catch(Exception e) {}
                synchronized(lock1) {
                    System.out.println("Thread2 over");
                }
            }
        }) .start();
        return "deadlock";
    }
    public static List<Long> getPartneridsFromJson(String data){
        //{\"data\":[{\"partnerid\":982,\"count\":\"10000\",\"cityid\":\"11\"},{\"partnerid\":983,\"count\":\"10000\",\"cityid\":\"11\"},{\"partnerid\":984,\"count\":\"10000\",\"cityid\":\"11\"}]}
        //上面是正常的数据
        List<Long> list = new ArrayList<Long>(2);
        if(data == null || data.length() <= 0){
            return list;
        }
        int datapos = data.indexOf("data");
        if(datapos < 0){
            return list;
        }
        int leftBracket = data.indexOf("[",datapos);
        int rightBracket= data.indexOf("]",datapos);
        if(leftBracket < 0 || rightBracket < 0){
            return list;
        }
        String partners = data.substring(leftBracket+1,rightBracket);
        if(partners == null || partners.length() <= 0){
            return list;
        }
        while(partners!=null && partners.length() > 0){
            int idpos = partners.indexOf("partnerid");
            if(idpos < 0){
                break;
            }
            int colonpos = partners.indexOf(":",idpos);
            int commapos = partners.indexOf(",",idpos);
            if(colonpos < 0 || commapos < 0){
                //partners = partners.substring(idpos+"partnerid".length());//1
                continue;
            }
            String pid = partners.substring(colonpos+1,commapos);
            if(pid == null || pid.length() <= 0){
                //partners = partners.substring(idpos+"partnerid".length());//2
                continue;
            }
            try{
                list.add(Long.parseLong(pid));
            }catch(Exception e){
                //do nothing
            }
            partners = partners.substring(commapos);
        }
        return list;
    }
}
