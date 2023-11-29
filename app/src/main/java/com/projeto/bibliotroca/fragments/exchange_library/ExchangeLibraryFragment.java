package com.projeto.bibliotroca.fragments.exchange_library;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.adapters.ExchangeLibraryAdapter;
import com.projeto.bibliotroca.models.BookSimpleDTO;
import com.projeto.bibliotroca.services.BookService;

import java.util.ArrayList;
import java.util.List;

public class ExchangeLibraryFragment extends Fragment {
    ExchangeLibraryAdapter adapter;
    List<BookSimpleDTO> books = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return inflater.inflate(R.layout.exchange_library_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recycleList = view.findViewById(R.id.recycleList);
        recycleList.setLayoutManager(new LinearLayoutManager(getContext()));

        BookService bookService = new BookService();
        bookService.getListBook(books);

        TextView txtAmountItems = view.findViewById(R.id.txtAmountItems);

        String amountItems = "";
        if (books.size() > 0) {
            amountItems = "I " + books.size() + " itens";
        }
        txtAmountItems.setText(amountItems);

        List<BookSimpleDTO> filteredBooks = new ArrayList<>();

        String defaultMessage = "Livro cadastrado pelo funcionário BiblioTroca";

        for (BookSimpleDTO book : books) {
            if (!book.getName().equals(defaultMessage)) {
                filteredBooks.add(book);
            }
        }

        adapter = new ExchangeLibraryAdapter(getContext(), filteredBooks);
        recycleList.setAdapter(adapter);
    }
}