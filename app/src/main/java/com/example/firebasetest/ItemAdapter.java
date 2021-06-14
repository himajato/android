package com.example.firebasetest;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firebasetest.Model.Item;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private ArrayList<Item> mItemList;

    Context context;
    public interface itemClickListener{
        void onItemClick(int position);
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent,false);
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }

    public ItemAdapter(Context context,ArrayList<Item> itemList){
        this.context = context;
        this.mItemList = itemList;
    }
    @Override
    public void onBindViewHolder(@NonNull  ItemAdapter.ItemViewHolder holder, int position) {
        Item currentItem = mItemList.get(position);
        holder.mTilte.setText(currentItem.getTilte());
        holder.mMoney.setText(currentItem.getMoney()+"");
        holder.mDate.setText(currentItem.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,EditItem.class);
                intent.putExtra("item",mItemList.get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItemList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        public TextView mTilte;
        public TextView mMoney;
        public TextView mDate;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mTilte = itemView.findViewById(R.id.cardTitle);
            mMoney = itemView.findViewById(R.id.cardMoney);
            mDate = itemView.findViewById(R.id.cardDate);
        }
    }


}
