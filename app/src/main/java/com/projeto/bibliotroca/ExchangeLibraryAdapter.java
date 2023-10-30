package com.projeto.bibliotroca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExchangeLibraryAdapter extends RecyclerView.Adapter<ExchangeLibraryItemView> {
    private Context context;
    private LayoutInflater inflater;

    public ExchangeLibraryAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ExchangeLibraryItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.exchange_library_item, parent, false);
        return new ExchangeLibraryItemView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeLibraryItemView holder, int position) {
        // função para pegar o obj a partir da posição, para setar no ITEM.
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}