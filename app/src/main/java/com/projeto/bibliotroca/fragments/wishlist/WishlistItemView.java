package com.projeto.bibliotroca.fragments.wishlist;

import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.R;

public class WishlistItemView extends RecyclerView.ViewHolder {

    public TextView txtWishTitleCard;
    public TextView txtWishAuthorCard;
    public TextView txtWishCategoryCard;
    public TextView txtWishDateCard;

    public ConstraintLayout wishCard;

    public View btnWishOptions;

    public WishlistItemView(View itemView) {
        super(itemView);

        txtWishTitleCard = itemView.findViewById(R.id.txtWishTitleCard);
        txtWishAuthorCard = itemView.findViewById(R.id.txtWishAuthorCard);
        txtWishCategoryCard = itemView.findViewById(R.id.txtWishCategoryCard);
        txtWishDateCard = itemView.findViewById(R.id.txtWishDateCard);

        wishCard = itemView.findViewById(R.id.Card_Wish_item);

        btnWishOptions = itemView.findViewById(R.id.btnWishOptionsCard);
    }
}