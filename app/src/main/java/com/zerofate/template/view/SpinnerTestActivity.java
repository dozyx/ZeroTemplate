package com.zerofate.template.view;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.zerofate.androidsdk.util.Utils;
import com.zerofate.template.R;
import com.zerofate.template.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SpinnerTestActivity extends AppCompatActivity {

    @BindView(R.id.dialog_spinner)
    Spinner dialogSpinner;
    @BindView(R.id.dropdown_spinner)
    Spinner dropdownSpinner;
    @BindView(R.id.popup)
    Button popup;

    PopupWindow window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spinner_test);
        ButterKnife.bind(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, Constants.sMessages);
        dialogSpinner.setAdapter(adapter);
        dropdownSpinner.setAdapter(adapter);
        window = new PopupWindow(this);
    }

    @OnClick(R.id.popup)
    public void popup() {
        if (window.isShowing()) {
            window.dismiss();
            return;
        }
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher_round);
        if (Utils.hasLollipop()) {
            window.setElevation(100);
        }
        //
//        window.setOverlapAnchor(true);
        window.setFocusable(true);// 效果类似于modal,接收全部触摸事件，如果点击外围部分将隐藏
        window.setClippingEnabled(true);// 是否允许超出屏幕，默认将摆放到边缘，为 flase 将允许 window 按精确位置放置（在这里验证时，不知道为什么只有垂直方向生效）
        window.setContentView(imageView);
        window.setWidth(300);
        window.showAsDropDown(popup);
        dimBehind(window);
    }

    public static void dimBehind(PopupWindow popupWindow) {
        View container;
        if (popupWindow.getBackground() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                container = (View) popupWindow.getContentView().getParent();
            } else {
                container = popupWindow.getContentView();
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                container = (View) popupWindow.getContentView().getParent().getParent();
            } else {
                container = (View) popupWindow.getContentView().getParent();
            }
        }
        Context context = popupWindow.getContentView().getContext();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams p = (WindowManager.LayoutParams) container.getLayoutParams();
        p.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        p.dimAmount = 0.9f;
        wm.updateViewLayout(container, p);
    }


}
