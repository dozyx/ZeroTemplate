package com.zerofate.template.justfortest.purchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zerofate.template.justfortest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PurchaseActivity extends Activity {

    @BindView(R.id.purchase_list)
    RecyclerView purchaseList;
    private PurchaseAdapter purchaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        purchaseList.setLayoutManager(new LinearLayoutManager(this));
        purchaseAdapter = new PurchaseAdapter(this);
        ArrayList<Goods> datas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            datas.add(new Goods("香蕉" + " # " + i, 1.2f, 0));
            datas.add(new Goods("苹果" + " # " + i, 3.2f, 0));
            datas.add(new Goods("西瓜" + " # " + i, 1.7f, 0));
            datas.add(new Goods("葡萄" + " # " + i, 8.2f, 0));
        }
        purchaseList.setAdapter(purchaseAdapter);
        purchaseAdapter.setData(datas);
    }

    public static class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.PurchaseHolder> {
        private List<Goods> datas = new ArrayList<>();
        private Context context;

        public PurchaseAdapter(Context context) {
            this.context = context;
        }

        @Override
        public PurchaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.purchase_item, parent, false);
            return new PurchaseHolder(view);
        }

        @Override
        public void onBindViewHolder(final PurchaseHolder holder, final int position) {
            final Goods goods = datas.get(position);
            holder.nameText.setText(goods.getName());
            holder.priceText.setText(goods.getPrice() + "");
            holder.countText.setText(goods.getCount() + "");
            holder.addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = goods.getCount() + 1;
                    goods.setCount(count);
                    holder.countText.setText(count + "");
                    notifyItemChanged(position);
                    notifyNewGoods(goods.getName(), goods.getPrice(), 1);
                }
            });
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = goods.getCount() - 1;
                    if (count < 0) {
                        return;
                    }
                    goods.setCount(count);
                    holder.countText.setText(count + "");
                    notifyItemChanged(position);
                    notifyNewGoods(goods.getName(), goods.getPrice(), -1);
                }
            });
        }

        public void notifyNewGoods(String name, float price, int count) {
            Intent intent = new Intent("com.yeahka.pad.action.ADD_GOODS");
            intent.putExtra("name", name);
            intent.putExtra("price", price);
            intent.putExtra("count", count);
            context.sendBroadcast(intent);
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        public void setData(ArrayList<Goods> datas) {
            this.datas = datas;
            notifyDataSetChanged();
        }

        public class PurchaseHolder extends RecyclerView.ViewHolder {
            TextView nameText;
            TextView priceText;
            TextView countText;
            ImageView addBtn;
            ImageView deleteBtn;

            public PurchaseHolder(View itemView) {
                super(itemView);
                nameText = itemView.findViewById(R.id.item_name);
                priceText = itemView.findViewById(R.id.item_price);
                countText = itemView.findViewById(R.id.count_show);
                addBtn = itemView.findViewById(R.id.add_item);
                deleteBtn = itemView.findViewById(R.id.delete_item);
            }
        }
    }
}
