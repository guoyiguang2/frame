package com.gupaoedu.vip.model;

import java.util.Objects;

public class Person {
    private String name;
    private String sex;
    private int age;

    private User user;

    public Person() {
        System.out.println("com.gupaoedu.vip.model.Person() 被实例化......");
    }

    public Person(String name, String sex, int age,User user) {
        System.out.println("com.gupaoedu.vip.model.Person(String name, String sex, int age) 被实例化......");
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.user = user;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("依赖注入反射调用com.gupaoedu.vip.model.Person.setName(String name)...参数为 ： "+name);
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        System.out.println("依赖注入反射调用com.gupaoedu.vip.model.Person.setSex(String sex)...参数为 ： "+sex);
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("依赖注入反射调用com.gupaoedu.vip.model.Person.setAge(int age)...参数为 ： "+age);
        this.age = age;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        System.out.println("依赖注入反射调用com.gupaoedu.vip.model.Person.setUser(User user)...参数为 ： "+user.toString());
        this.user = user;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(sex, person.sex) &&
                Objects.equals(user, person.user);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, sex, age, user);
    }
}
