package cn.dozyx.template.util;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MockUtils {
    public static void setupList(RecyclerView recyclerView, List<String> datas) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(android.R.layout.simple_list_item_1, datas) {
            @Override
            protected void convert(@NotNull BaseViewHolder helper, String s) {
                helper.setText(android.R.id.text1, s);
            }
        });
    }
}
