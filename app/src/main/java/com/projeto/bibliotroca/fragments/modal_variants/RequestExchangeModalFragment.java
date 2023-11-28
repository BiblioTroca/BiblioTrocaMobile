package com.projeto.bibliotroca.fragments.modal_variants;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.projeto.bibliotroca.ProfileActivity;
import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.models.BookCompleteDTO;
import com.projeto.bibliotroca.models.BuyerDTO;
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.services.BookService;
import com.projeto.bibliotroca.services.TransactionService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class RequestExchangeModalFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(getDialog() != null && getDialog().getWindow() != null) {
            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        return inflater.inflate(R.layout.request_exchange_modal_fragment, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(getDialog() != null && getDialog().getWindow() != null) {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

            int dialogWidth = displayMetrics.widthPixels - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources().getDisplayMetrics()); // Subtrai 24dp de cada lado
            int dialogHeight = ViewGroup.LayoutParams.WRAP_CONTENT;

            getDialog().getWindow().setLayout(dialogWidth, dialogHeight);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnRequestExchange = view.findViewById(R.id.btnRequestExchange);
        btnRequestExchange.setOnClickListener(event -> {
            prepareNewTransaction();

            // futuramente será alterado para a de trocas pendentes
            // requireContext() is the security version (non-null) of getContext(),
            Intent openPendingExchanges = new Intent(requireContext(), ProfileActivity.class);
            startActivity(openPendingExchanges);
        });

        ImageView btnCloseModal = view.findViewById(R.id.btnCloseModal);
        btnCloseModal.setOnClickListener(event -> {
            dismiss();
        });
        Button btnBack = view.findViewById(R.id.btnRegisterAccount);
        btnBack.setOnClickListener(event -> {
            dismiss();
        });
    }

    private void prepareNewTransaction() {
        BookService bookService = new BookService();
        TransactionService transactionService = new TransactionService();

        BookCompleteDTO book;
        BuyerDTO buyer = new BuyerDTO();
        TransactionDTO transaction = new TransactionDTO();

        Intent receivedIntentFromItem = requireActivity().getIntent();

        if (receivedIntentFromItem == null) throw new Error("Book id doesn't exist");

        String bookId = receivedIntentFromItem.getStringExtra("bookId");
        book = bookService.getBookById(bookId);

        // construir o buyer a partir das infos do token depois
        buyer.setFirstName("Mateus");
        buyer.setLastName("Silva");
        buyer.setEmail("email@example.com");
        buyer.setAverageRating(4.1);
        buyer.setAvaliationsNumber(30);
        buyer.setPhoneNumber("11943464400");

        Date currentDate = new Date();
        String formatToConvert = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat dateFormater = new SimpleDateFormat(formatToConvert, Locale.getDefault());

        String createdAt = dateFormater.format(currentDate);
        String transactionId = UUID.randomUUID().toString();

        transaction.setId(transactionId);
        transaction.setStatus("Pendente");
        transaction.setType("receive");
        transaction.setCreatedAt(createdAt);
        transaction.setBookDetails(book);
        transaction.setBuyer(buyer);

        transactionService.createTransaction(transaction);
    }
}
