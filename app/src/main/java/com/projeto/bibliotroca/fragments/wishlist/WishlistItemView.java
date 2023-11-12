package com.projeto.bibliotroca.fragments.wishlist;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.R;

public class WishlistItemView extends RecyclerView.ViewHolder {

    public View btnOptions;
    public WishlistItemView(View itemView) {
        super(itemView);
   btnOptions = itemView.findViewById(R.id.btn_options);
}
}