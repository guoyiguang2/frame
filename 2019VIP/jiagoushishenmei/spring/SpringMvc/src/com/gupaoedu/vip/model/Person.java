package com.gupaoedu.vip.model;

import java.util.Objects;

public class Person {
    private String name;
    private String sex;
    private int age;

    public Person() {
        System.out.println("com.gupaoedu.vip.model.Person() 被实例化......");
    }

    public Person(String name, String sex, int age) {
        System.out.println("com.gupaoedu.vip.model.Person(String name, String sex, int age) 被实例化......");
        this.name = name;
        this.sex = sex;
        this.age = age;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age &&
                Objects.equals(name, person.name) &&
                Objects.equals(sex, person.sex);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, sex, age);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }
}
