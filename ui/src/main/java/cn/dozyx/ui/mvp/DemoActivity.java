package cn.dozyx.ui.mvp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.Random;

import cn.dozyx.ui.mvp.base.BaseMVPActivity;
import cn.dozyx.zerofate.ui.R;

/**
 * Create by T on 2019/11/26
 **/
public class DemoActivity extends BaseMVPActivity<DemoPresenter> implements DemoView {
    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.text);
        button.setOnClickListener(v -> presenter.loadData());
    }

    @Override
    protected DemoPresenter createPresenter() {
        return new DemoPresenter(this);
    }

    @Override
    public void showSomething() {
        textView.setText(textView.getText() + "123");
    }
}
