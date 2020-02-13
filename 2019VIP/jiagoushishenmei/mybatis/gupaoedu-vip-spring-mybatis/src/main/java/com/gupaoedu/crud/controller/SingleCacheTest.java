package com.gupaoedu.crud.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gupaoedu.crud.bean.*;
import com.gupaoedu.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 单例模式结合 hashTable 作缓存 测试
 */
public class SingleCacheTest {

    private static Map<String,Object>  cacheManager = null;
    private static Map<String,Map<String,Object>>  cacheManagers = null;

    private static SingleCacheTest instance = new SingleCacheTest();
    private SingleCacheTest(){
        cacheManager = new HashMap<String,Object>();
        cacheManagers = new HashMap<String,Map<String,Object>>();
    }
    public static SingleCacheTest getInstance(){
        return instance;
    }


    public static Map<String,Object> getCacheManager(){
        return cacheManager;
    }

    public static Map<String,Map<String,Object>> getCacheManagers(){
        return cacheManagers;
    }


    public static Map<String,Map<String,Object>> putValue(String serviceFullName,String methodName,String args,Object value){
        Map  classMap = cacheManagers.get(serviceFullName);
        if(null == classMap){
            classMap = new HashMap<String,Object>();
        }
        classMap.put(methodName+args,value);
        cacheManagers.put(serviceFullName,classMap);
        return cacheManagers;
    }

    public static void main(String[] args) throws ClassNotFoundException {


    }

    public static Department getDepartment(){
        Department department = new Department();
        department.setDeptId(1);
        department.setDeptName("研发部");
        return department;
    }

    public static String objectToString(){
        //Student student=  getStudent();
        Department department = new Department();
        department.setDeptId(1);
        department.setDeptName("慕容博");
        String jsonString = JSON.toJSONString(department);
        System.out.println(jsonString);
        return jsonString;
    }

    public static String arrayToString(){
        List<Address> addresses=  getArray();
        String jsonString = JSON.toJSONString(addresses);
        System.out.println(jsonString);
        return jsonString;
    }

    public static Student getStudent(){

        List<Address> addressArrayList = new ArrayList<Address>();
        Address dept = new Address();
        dept.setDetail("北京");

        Address dept2 = new Address();
        dept2.setDetail("上海");
        addressArrayList.add(dept);
        addressArrayList.add(dept2);

        Student student = new Student();
        student.setName("张三");
        student.setAddresses(addressArrayList);

        return student;
    }

    public static List<Address> getArray(){

        List<Address> addressArrayList = new ArrayList<Address>();
        Address address1 = new Address();
        address1.setDetail("北京");

        Address address2 = new Address();
        address2.setDetail("上海");
        addressArrayList.add(address1);
        addressArrayList.add(address2);


        return addressArrayList;
    }
}
