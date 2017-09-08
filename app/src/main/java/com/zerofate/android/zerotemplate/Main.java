package com.zerofate.android.zerotemplate;


import android.os.Parcel;
import android.os.Parcelable;

import org.w3c.dom.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Main {

    public static void main(String[] args) {
        User user = new User("张三", 26, new Test("李四"));
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("serializabletest.txt"));
            out.writeObject(user);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("serializabletest"
                        + ".txt"));
            User newUser = (User) in.readObject();
            System.out.println(newUser.name + " & " + newUser.age + " & " + newUser.test.string);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private static class User implements Serializable{
        String name;
        int age;
        Test test;

        public User(String name, int age,Test test) {
            this.name = name;
            this.age = age;
            this.test = test;
        }

        public User(Parcel parcel){

        }


    }

    private static class Test implements Parcelable{
        String string;

        public Test(String string){
            this.string = string;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }

        public static final Creator<User> CREATOR= new Creator<User>(){
            @Override
            public User createFromParcel(Parcel source) {
                return new User(source);
            }

            @Override
            public User[] newArray(int size) {
                return new User[0];
            }
        };

    }

}
