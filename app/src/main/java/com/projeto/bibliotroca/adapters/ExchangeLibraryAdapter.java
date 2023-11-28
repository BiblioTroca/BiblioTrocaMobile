package com.projeto.bibliotroca.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.SelectedExchangeLibraryActivity;
import com.projeto.bibliotroca.fragments.exchange_library.ExchangeLibraryItemView;
import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.models.BookSimpleDTO;

import java.util.List;

public class ExchangeLibraryAdapter extends RecyclerView.Adapter<ExchangeLibraryItemView> {
    Context context;
    private LayoutInflater inflater;
    List<BookSimpleDTO> books;

    public ExchangeLibraryAdapter(Context context, List<BookSimpleDTO> books) {
        this.context = context;
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

        if (book != null && book.getSeller() != null) {
            itemView.txtBookName.setText(book.getName());
            itemView.txtAuthor.setText("por " + book.getAuthor());
            itemView.txtDescription.setText(book.getShortDescription() + "...");
            itemView.txtCategory.setText(book.getCategory());
            itemView.txtSellerName.setText("Enviado por " + book.getSeller().getName() + book.getSeller().getSurname());
            itemView.txtLocation.setText(book.getSeller().getLocation());
            itemView.txtAverageRating.setText(String.valueOf(book.getSeller().getAverageRating()));
            itemView.txtAvaliationsNumber.setText("(" + String.valueOf(book.getSeller().getAvaliationsNumber()) + ")");

        }
            itemView.btnShowItemDetails.setOnClickListener(event -> {
                Intent openSelectedExchangeLibrary = new Intent(context, SelectedExchangeLibraryActivity.class);
                openSelectedExchangeLibrary.putExtra("bookId", book.getId());
                context.startActivity(openSelectedExchangeLibrary);
            });
        }

    @Override
    public int getItemCount() {
        return books.size();
    }
}