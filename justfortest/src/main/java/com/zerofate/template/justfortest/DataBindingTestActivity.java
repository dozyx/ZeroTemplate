package com.zerofate.template.justfortest;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.zerofate.template.justfortest.databinding.ActivityDataBindingTestBinding;

public class DataBindingTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDataBindingTestBinding dataBindingTestActivity = DataBindingUtil.setContentView(
                this, R.layout.activity_data_binding_test);
        dataBindingTestActivity.btnTest.setOnClickListener(v -> {
            User user = new User();
            user.name = "你好";
            user.address = new User.Address();
            user.address.province = "广东";
            dataBindingTestActivity.setUser(user);
        });
    }

    public static class User {
        private String name;
        private Address address;

        public static class Address {
            public String province;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Address getAddress() {
            return address;
        }

        public void setAddress(Address address) {
            this.address = address;
        }
    }
}
