package com.nick.learning.jvm.reference;

public class Student {

    private String name;

    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public void finalize(){
        System.out.println("student 被回收了");
    }

    @Override
    public String toString(){
        return name + " : " + age;
    }
}
