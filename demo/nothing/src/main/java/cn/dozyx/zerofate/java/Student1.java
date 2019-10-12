package cn.dozyx.zerofate.java;

import com.google.gson.annotations.SerializedName;

/**
 * Create by dozyx on 2019/3/27
 **/
public class Student1 {

    /**
     * name : 1
     * root.age : 19
     */

    private String name;
    @SerializedName("root.age")
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student1{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
