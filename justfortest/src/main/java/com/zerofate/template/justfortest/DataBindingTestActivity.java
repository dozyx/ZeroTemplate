package com.zerofate.template.justfortest;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.zerofate.androidsdk.util.ToastX;
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
        dataBindingTestActivity.setHandler(new EventHandler());
        dataBindingTestActivity.setPresenter(new Presenter());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void onProvinceClicked(View view) {
        ToastX.showShort(this, "点击");
    }

    public class EventHandler{
        public void onClick(View view){
            ToastX.showShort(DataBindingTestActivity.this,"onClick view");
        }
    }

    public class Presenter{
        public void onClick(){
            ToastX.showShort(DataBindingTestActivity.this,"onClick presenter");
        }
        public void onClickRoot(){
            ToastX.showShort(DataBindingTestActivity.this,"onClickRoot presenter");
        }
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
