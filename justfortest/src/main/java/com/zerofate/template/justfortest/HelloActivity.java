package com.zerofate.template.justfortest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerofate.androidsdk.util.ToastX;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends Activity {


    @BindView(R.id.btn_hello)
    Button btnHello;
    @BindView(R.id.edit_test)
    CustomEditText editTest;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.image_clip)
    ImageView imageClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        ButterKnife.bind(this);
        if (getIntent() != null) {
            Person person = getIntent().getParcelableExtra("person");
            ToastX.showShort(this, person + "");
        }
    }

    @OnClick(R.id.btn_hello)
    public void onHello() {
        Person zhangsan = new Person("张三", 18);
        ToastX.showShort(this, zhangsan + "");
        Bundle bundle = new Bundle();
        bundle.putParcelable("person",zhangsan);
        Person lisi = bundle.getParcelable("person");
        lisi.name = "李四";
        ToastX.showShort(this,zhangsan.name +"");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ToastX.showShort(this, "onNewIntent -> ");
    }
}
