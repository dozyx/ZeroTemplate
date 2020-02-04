package cn.dozyx.template.justfortest;


import com.dozex.autobuilder.annotation.Builder;

/**
 * @author dozeboy
 * @date 2017/12/28
 */
@Builder
public class User {
    public String name;
    public int age;

    public User() {
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
