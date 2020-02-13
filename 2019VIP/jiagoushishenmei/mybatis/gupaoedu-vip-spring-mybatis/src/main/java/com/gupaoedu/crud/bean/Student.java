package com.gupaoedu.crud.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gupaoedu.crud.controller.SingleCacheTest;

import java.io.Serializable;
import java.util.List;


public class Student implements Serializable{


    private String name;

    private List<Address> addresses;

    public Student() {
    }



    @MyAnnotation(isList=false,returnType = "java.lang.Integer")
    public Integer getAge() {
        return 27;
    }


    @MyAnnotation(isList=false,returnType = "java.lang.String")
    public String getName() {
        return "慕容白";
    }

    @MyAnnotation(isList=false,returnType = "com.gupaoedu.crud.bean.Department")
    public Department getDepartment() {
        Department Department = SingleCacheTest.getDepartment();
        return Department;
    }

    @MyAnnotation(isList=true,returnType = "com.gupaoedu.crud.bean.Student")
    public List<Address> getAddresses() {
        return addresses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}