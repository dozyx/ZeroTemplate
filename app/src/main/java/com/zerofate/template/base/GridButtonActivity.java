package com.zerofate.template.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.zerofate.template.R;

public abstract class GridButtonActivity extends AppCompatActivity {
    private GridLayout gridLayout;
    private TextView textLog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_button);
        gridLayout = (GridLayout) findViewById(R.id.grid);
        textLog = (TextView) findViewById(R.id.text_log);
    }

    protected void addButton(String text, Runnable task) {
        Button button = new Button(this);
        button.setText(text);
        button.setId(View.generateViewId());
        button.setOnClickListener(v -> task.run());
        gridLayout.addView(button);
    }

    protected void appendLog(String log) {
        String previousLog = (String) textLog.getText();
        if (TextUtils.isEmpty(previousLog)) {
            previousLog = "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append(log).append("\n").append(previousLog);
        textLog.setText(builder.toString());
    }
}
