package com.zerofate.template;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.zerofate.template.base.BaseShowResultActivity;

/**
 * 不同 Activity 间进行序列化与反序列化得到的是不同实例
 */
public class ParcelableTestActivity extends BaseShowResultActivity {

    private Person person;

    @Override
    protected String[] getButtonText() {
        return new String[]{"发送数据", "显示Intent数据", "修改数据", "显示 Person"};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onButton1() {
        person = new Person("张三", 18);
        Intent intent = new Intent(this, ParcelableTestActivity.class);
        intent.putExtra("person", person);
        startActivity(intent);
    }

    @Override
    public void onButton2() {
        if (getIntent() != null) {
            person = getIntent().getParcelableExtra("person");
            appendResult(person.toString());
        }
    }

    @Override
    public void onButton3() {
        if (person != null) {
            person.name = "李四";
        }
    }

    @Override
    public void onButton4() {
        appendResult(person.toString());
    }

    private static class Person implements Parcelable {
        String name;
        int age;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.name);
            dest.writeInt(this.age);
        }

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public Person() {
        }

        protected Person(Parcel in) {
            this.name = in.readString();
            this.age = in.readInt();
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }

        public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
            @Override
            public Person createFromParcel(Parcel source) {
                return new Person(source);
            }

            @Override
            public Person[] newArray(int size) {
                return new Person[size];
            }
        };
    }
}
