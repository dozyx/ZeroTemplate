package com.zerofate.template.practice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zerofate.andoroid.data.Shakespeare;
import com.zerofate.androidsdk.util.ViewUtil;
import com.zerofate.template.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ScrollView 中嵌套 ListView，在 google 过程中，我看到的很多建议，其实是"don’t do it"。
 * 不过本着学习的目的，研究一下也没什么关系。
 */
public class ListInsideScrollTest extends AppCompatActivity {

    @BindView(R.id.list)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_inside_scroll);
        ButterKnife.bind(this);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                Shakespeare.MORE_TITLES));
//        ViewUtil.setListViewHeightBasedOnChildren(listView);
    }

}
