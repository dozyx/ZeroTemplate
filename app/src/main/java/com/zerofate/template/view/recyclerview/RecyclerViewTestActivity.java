package com.zerofate.template.view.recyclerview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zerofate.andoroid.data.Shakespeare;
import com.zerofate.template.R;
import com.zerofate.template.databinding.ActivityRecyclerViewTestBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;

public class RecyclerViewTestActivity extends AppCompatActivity {

    private static final String TAG = "RecyclerViewTestActivit";
    private ActivityRecyclerViewTestBinding binding;
    private List<String> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recycler_view_test);
        ButterKnife.bind(this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        datas = getRandomStrings();
        binding.recyclerView.setAdapter(new RecyclerView.Adapter<CustomViewHolder>() {
            @NonNull
            @Override
            public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                    int viewType) {
                return new CustomViewHolder(LayoutInflater.from(RecyclerViewTestActivity.this).inflate(R.layout.item_text,null));
            }

            @Override
            public void onBindViewHolder(@NonNull CustomViewHolder holder,
                    int position) {
                Log.d(TAG, "onBindViewHolder: " + position);
                holder.textView.setText(datas.get(position));
            }

            @Override
            public int getItemCount() {
                return datas.size();
            }
        });
        binding.btnChangeData.setOnClickListener(v -> {
            List<String> newDatas = getRandomStrings();
            datas = newDatas;
            Log.d(TAG, "notify before ");
            binding.recyclerView.getAdapter().notifyDataSetChanged();
            Log.d(TAG, "onCreate: notify after");
        });

    }

    @NonNull
    private List<String> getRandomStrings() {
        List<String> newDatas = new ArrayList<>(5);
        for (int i = 0;i<5;i++){
            newDatas.add(Shakespeare.TITLES[new Random().nextInt(Shakespeare.TITLES.length)]);
        }
        return newDatas;
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private static class CustomViewHolder extends RecyclerView.ViewHolder{

        protected TextView textView;
        public CustomViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }


}
