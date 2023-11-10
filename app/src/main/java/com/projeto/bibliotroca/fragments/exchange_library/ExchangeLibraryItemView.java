package com.projeto.bibliotroca.fragments.exchange_library;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.R;

// Classe que contém a lógica de apresentação e interação para um único item da lista.
public class ExchangeLibraryItemView extends RecyclerView.ViewHolder {
    public TextView txtBookName;
    public TextView txtAuthor;
    public TextView txtDescription;
    public TextView txtCategory;
    public TextView txtSellerName;
    public TextView txtLocation;
    public TextView txtAverageRating;
    public TextView txtAvaliationsNumber;
    public TextView btnShowItemDetails;

    public ExchangeLibraryItemView(@NonNull View itemView) {
        super(itemView);

        txtBookName = itemView.findViewById(R.id.txtBookName);
        txtAuthor = itemView.findViewById(R.id.txtAuthor);
        txtDescription = itemView.findViewById(R.id.txtDescription);
        txtCategory = itemView.findViewById(R.id.txtCategory);
        txtSellerName = itemView.findViewById(R.id.txtSellerName);
        txtLocation = itemView.findViewById(R.id.txtLocation);
        txtAverageRating = itemView.findViewById(R.id.txtAverageRating);
        txtAvaliationsNumber = itemView.findViewById(R.id.txtAvaliationsNumber);

        btnShowItemDetails = itemView.findViewById(R.id.btnShowItemDetails);
    }
}