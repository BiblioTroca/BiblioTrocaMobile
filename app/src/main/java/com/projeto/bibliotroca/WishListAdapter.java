package com.projeto.bibliotroca;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class WishListAdapter extends RecyclerView.Adapter<WishListItemView> {
    private Context context;
    private LayoutInflater inflater;

    public WishListAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public WishListItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.wishlist_item, parent, false);
        return new WishListItemView(itemView);



    }

    @Override
    public void onBindViewHolder(@NonNull WishListItemView holder, int position) {
        // função para pegar o obj a partir da posição, para setar no ITEM.
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
