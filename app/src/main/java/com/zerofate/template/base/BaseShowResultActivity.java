package com.zerofate.template.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.zerofate.template.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 用于基本的按键-显示操作
 */
public abstract class BaseShowResultActivity extends AppCompatActivity implements IBaseView {
    private static final String TAG = "BaseShowResultActivity";

    @BindView(R.id.button)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.result_text)
    TextView resultText;
    @BindView(R.id.result_image)
    ImageView resultImage;

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

    @OnClick(R.id.button5)
    public void onButton5() {

    }

    @OnClick(R.id.button6)
    public void onButton6() {

    }


    /**
     * 感觉还是矬。。。
     */
    protected void setButtonText(@NonNull String[] texts) {
        Button[] buttons = new Button[]{button1, button2, button3, button4, button5, button6};
        for (int i = 0; i < texts.length; i++) {
            setButtonText(buttons[i], texts[i]);
            buttons[i].setVisibility(View.VISIBLE);
        }
    }

    protected void setButtonText(@NonNull Button button, @NonNull String text) {
        button.setText(text);
    }

    public void setText(String text) {
        resultText.setText(text);
    }


    @Override
    public void clearResult() {
        setText("");
    }

    @Override
    public void appendResult(String text) {
        resultText.setText(resultText.getText() + "\n" + text);
        Log.d(TAG, "appendResult: " + text);
    }


    protected void setImage(@DrawableRes int drawable) {
        resultImage.setImageResource(drawable);
    }

    protected void setImage(Drawable drawable) {
        resultImage.setImageDrawable(drawable);
    }

    protected abstract String[] getButtonText();

}
