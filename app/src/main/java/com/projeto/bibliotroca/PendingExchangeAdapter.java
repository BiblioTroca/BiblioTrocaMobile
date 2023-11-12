package com.projeto.bibliotroca;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PendingExchangeAdapter extends RecyclerView.Adapter<PendingExchangeItemView> {

    private Context context;
    private LayoutInflater inflater;

    public PendingExchangeAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PendingExchangeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.trades_item, parent, false);
        return new PendingExchangeItemView(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull PendingExchangeItemView itemView, int position) {


        itemView.textView63.setOnClickListener(event -> {
            Intent openSelectedExchange = new Intent(context, SelectedExchangeActivity.class);
            context.startActivity(openSelectedExchange);
        });    }

    @Override
    public int getItemCount() {
        return 3;
    }
}

