package com.zerofate.template.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.zerofate.template.R;

/**
 * @author dozeboy
 * @date 2018/7/1
 */
public class BaseGridButtonFragment extends Fragment {
    private GridLayout gridLayout;
    private TextView textLog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_grid_button,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridLayout = (GridLayout) view.findViewById(R.id.grid);
        textLog = (TextView) view.findViewById(R.id.text_log);
    }

    protected void addButton(String text, Runnable task) {
        Button button = new Button(getActivity());
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
