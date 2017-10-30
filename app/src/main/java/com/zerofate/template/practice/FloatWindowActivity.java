package com.zerofate.template.practice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zerofate.andoroid.data.Shakespeare;
import com.zerofate.template.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FloatWindowActivity extends AppCompatActivity {

    private boolean isFullScreen = true;
    Window window;
    WindowManager.LayoutParams params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float_window);
        ButterKnife.bind(this);
        /*window = getWindow();
        params = window.getAttributes();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.format = PixelFormat.RGBA_8888;
        params.gravity = Gravity.RIGHT;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);*/
    }

    @OnClick(R.id.float_button)
    public void onFloatButton() {

        /*params.gravity = Gravity.RIGHT;
        if (isFullScreen) {
            params.width = getResources().getDisplayMetrics().widthPixels / 2;
        } else {
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
        }
        window.setAttributes(params);
        isFullScreen = !isFullScreen;*/

        Intent intent = new Intent(this, FloatWindowsServices.class);
        if (FloatWindowsServices.isServiceRunning) {
            stopService(intent);
            return;
        }
        startService(intent);
    }

    public static class FloatWindowsServices extends Service {

        private View windowView;
        private Button button1;
        private Button button2;
        private ListView listView;
        private WindowManager windowManager;
        private WindowManager.LayoutParams params;

        public static boolean isServiceRunning;

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public void onCreate() {
            super.onCreate();
            windowManager = (WindowManager) getApplication().getSystemService(Context.WINDOW_SERVICE);
            params = new WindowManager.LayoutParams();
            showFloat();
            isServiceRunning = true;
        }

        public void showFloat() {
            windowView = LayoutInflater.from(this).inflate(R.layout.float_window, null);

            params.type = WindowManager.LayoutParams.TYPE_PHONE;
            params.format = PixelFormat.TRANSLUCENT;
            params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_FULLSCREEN;
            params.gravity = Gravity.RIGHT | Gravity.CENTER_VERTICAL;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.MATCH_PARENT;

            windowManager.addView(windowView, params);

            button1 = (Button) windowView.findViewById(R.id.float_button1);
            button2 = (Button) windowView.findViewById(R.id.float_button2);
            listView = (ListView) windowView.findViewById(R.id.list);
            listView.setAdapter(new ArrayAdapter<>(FloatWindowsServices.this, android.R.layout.simple_list_item_1, Shakespeare.MORE_TITLES));
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    params.width = 800;
                    windowManager.updateViewLayout(windowView, params);
                }
            });

            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    windowManager.updateViewLayout(windowView, params);
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(FloatWindowsServices.this, (String) (parent.getAdapter().getItem(position)), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            windowManager.removeView(windowView);
            isServiceRunning = false;
        }
    }
}
