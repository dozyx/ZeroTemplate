package cn.dozyx.proguardtest.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import cn.dozyx.lib.EnumClass;
import cn.dozyx.lib.MyClass;

public class MainActivity extends AppCompatActivity {

    private EnumClass enumClass = EnumClass.Apple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proguard);

        MyClass myClass = new MyClass();
        myClass.doPublic("哈哈");


    }
}