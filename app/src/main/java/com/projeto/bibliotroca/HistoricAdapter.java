package com.projeto.bibliotroca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistoricAdapter extends RecyclerView.Adapter<HistoricItemView> {
    private Context context;
    private LayoutInflater inflater;

    public HistoricAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HistoricItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.historic_item, parent, false);
        return new HistoricItemView(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull HistoricItemView holder, int position) {
        // função para pegar o obj a partir da posição, para setar no ITEM.
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
