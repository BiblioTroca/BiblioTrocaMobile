package com.projeto.bibliotroca.fragments.mybooks;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.R;

public class MyBooksItemView extends RecyclerView.ViewHolder {

    public TextView txtBookTitleCard;
    public TextView txtBookAuthorCard;
    public TextView txtBookCategoryCard;
    public TextView txtBookDate;

    public ConstraintLayout CardBook;

    public View btnOptionsB;
    public MyBooksItemView(@NonNull View itemView) {
        super(itemView);

        txtBookTitleCard = itemView.findViewById(R.id.txtBookTitleCard);
        txtBookAuthorCard = itemView.findViewById(R.id.txtBookAuthorCard);
        txtBookCategoryCard= itemView.findViewById(R.id.txtBookCategoryCard);
        txtBookDate=itemView.findViewById(R.id.txtBookDate);

        CardBook=itemView.findViewById(R.id.Card_Book_item);
        btnOptionsB = itemView.findViewById(R.id.btn_options3);

    }


}
