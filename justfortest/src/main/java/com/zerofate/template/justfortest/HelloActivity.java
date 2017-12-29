package com.zerofate.template.justfortest;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerofate.androidsdk.util.ToastX;
import com.zerofate.template.justfortest.databinding.ActivityHelloBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends AppCompatActivity {


    @BindView(R.id.btn_hello)
    Button btnHello;
    @BindView(R.id.edit_test)
    EditText editTest;
    @BindView(R.id.text1)
    TextView text1;
    @BindView(R.id.image_clip)
    ImageView imageClip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHelloBinding binding = DataBindingUtil.setContentView(this,
                R.layout.activity_hello);
        ButterKnife.bind(this);
        ClipDrawable drawable = (ClipDrawable) imageClip.getDrawable();
        drawable.setLevel(1000);
//        binding.setUser(new User("大佬", 18));
    }

    @OnClick(R.id.btn_hello)
    public void onHello() {
        ToastX.showShort(this, Color.parseColor("#ffffff") + "");
        editTest.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
    }
}
