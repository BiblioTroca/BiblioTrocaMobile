package com.projeto.bibliotroca;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PendingExchangeItemView extends RecyclerView.ViewHolder{
/*
    public TextView txtBookName;
    public TextView txtAuthor;
    public TextView txtDescription;
    public TextView txtCategory;
    public TextView txtSellerName;
    public TextView txtLocation;
    public TextView txtAverageRating;
    public TextView txtAvaliationsNumber;
  */
public TextView textView63;


    public PendingExchangeItemView(@NonNull View itemView) {
        super(itemView);

        textView63 = itemView.findViewById(R.id.textView63);



/*        txtBookName = itemView.findViewById(R.id.txtBookName);
        txtAuthor = itemView.findViewById(R.id.txtAuthor);
        txtDescription = itemView.findViewById(R.id.txtDescription);
        txtCategory = itemView.findViewById(R.id.txtCategory);
        txtSellerName = itemView.findViewById(R.id.txtSellerName);
        txtLocation = itemView.findViewById(R.id.txtLocation);
        txtAverageRating = itemView.findViewById(R.id.txtAverageRating);
        txtAvaliationsNumber = itemView.findViewById(R.id.txtAvaliationsNumber);
  */
    }

}
