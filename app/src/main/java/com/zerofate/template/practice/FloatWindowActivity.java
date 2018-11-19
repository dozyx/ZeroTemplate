package com.zerofate.template.practice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import androidx.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.zerofate.android.mock.Shakespeare;
import com.zerofate.template.R;
import com.zerofate.template.activity.LifeCycleTest;

public class FloatWindowActivity extends LifeCycleTest {

    WindowManager.LayoutParams params;

    @Override
    protected String[] getButtonText() {
        return new String[]{"开关","占位"};
    }

    @Override
    public void onButton1() {
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
