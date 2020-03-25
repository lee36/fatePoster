package com.lee.fateposter.test;

/**
 * @description TODO
 * @Author lhj
 * @create 2020/3/25 0025 21:02
 */
public class User {
    private Integer age;
    private String name;

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
