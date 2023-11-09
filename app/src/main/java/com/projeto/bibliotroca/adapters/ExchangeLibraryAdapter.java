package com.projeto.bibliotroca.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.fragments.exchange_library.ExchangeLibraryItemView;
import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.models.BookSimpleDTO;

import java.util.List;

public class ExchangeLibraryAdapter extends RecyclerView.Adapter<ExchangeLibraryItemView> {
    private LayoutInflater inflater;
    List<BookSimpleDTO> books;

    public ExchangeLibraryAdapter(Context context, List<BookSimpleDTO> books) {
        this.inflater = LayoutInflater.from(context);
        this.books = books;
    }

    @NonNull
    @Override
    public ExchangeLibraryItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.exchange_library_item, parent, false);
        return new ExchangeLibraryItemView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ExchangeLibraryItemView itemView, int position) {
        BookSimpleDTO book = books.get(position);

        itemView.txtBookName.setText(book.getName());
        itemView.txtAuthor.setText(book.getAuthor());
        itemView.txtDescription.setText(book.getShortDescription() + "...");
        itemView.txtCategory.setText(book.getCategory());
        itemView.txtSellerName.setText("Enviado por " + book.getSeller().getName());
        itemView.txtLocation.setText(book.getSeller().getLocation());
        itemView.txtAverageRating.setText(String.valueOf(book.getSeller().getAverageRating()));
        itemView.txtAvaliationsNumber.setText("(" + String.valueOf(book.getSeller().getAvaliationsNumber()) + ")");
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}