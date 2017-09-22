package com.zerofate.template.base;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.zerofate.template.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用于基本的按键-显示操作，包含 4 个 Button 和一个 TextView
 */
public abstract class BaseShowResultActivity extends AppCompatActivity {

    @BindView(R.id.button)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button4)
    Button button3;
    @BindView(R.id.button3)
    Button button4;
    @BindView(R.id.result)
    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_show_result);
        ButterKnife.bind(this);
        setButtonText(getButtonText());
    }

    @OnClick(R.id.button)
    public void onButton1() {

    }

    @OnClick(R.id.button2)
    public void onButton2() {

    }

    @OnClick(R.id.button3)
    public void onButton3() {

    }

    @OnClick(R.id.button4)
    public void onButton4() {

    }


    /**
     * 感觉还是矬。。。
     */
    protected void setButtonText(@NonNull String[] texts) {
        Button[] buttons = new Button[]{button1, button2, button3, button4};
        for (int i = 0; i < texts.length; i++) {
            setButtonText(buttons[i], texts[i]);
            buttons[i].setVisibility(View.VISIBLE);
        }
    }

    protected void setButtonText(@NonNull Button button, @NonNull String text) {
        button.setText(text);
    }

    protected void setResult(String text) {
        resultText.setText(text);
    }

    protected abstract String[] getButtonText();
}
