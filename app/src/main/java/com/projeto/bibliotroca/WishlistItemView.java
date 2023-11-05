package com.projeto.bibliotroca;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class WishlistItemView extends RecyclerView.ViewHolder {

    public View btnOptions;
    public WishlistItemView(View itemView) {
        super(itemView);
    btnOptions = itemView.findViewById(R.id.btn_options2);
}
}