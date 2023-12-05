package com.projeto.bibliotroca;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projeto.bibliotroca.models.TransactionDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class PendingExchangeAdapter extends RecyclerView.Adapter<PendingExchangeItemView> {

    private Context context;
    private LayoutInflater inflater;

    List<TransactionDTO> transactions;

    public PendingExchangeAdapter(Context context, List<TransactionDTO> transactions) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.transactions = transactions;
    }

    @NonNull
    @Override
    public PendingExchangeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.trades_item, parent, false);
        return new PendingExchangeItemView(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull PendingExchangeItemView itemView, int position) {
        TransactionDTO transaction = transactions.get(position);


        Log.d("E" , "onBindViewHolder: " + transaction);
        Log.d("Primeiro Nome" , "onBindViewHolder: " + transaction.getBuyer().getFirstName());
        Log.d("Id", "onBindViewHolder: " + transaction.getId());
        Log.d("DATA", "onBindViewHolder: " + transaction.getCreatedAt());

        if (transaction.getStatus().equalsIgnoreCase("pendente")) {
            itemView.textView63.setOnClickListener(event -> {
                Intent openSelectedExchange = new Intent(context, SelectedExchangeActivity.class);
                openSelectedExchange.putExtra("transactionId", transaction.getId());
                context.startActivity(openSelectedExchange);
                Log.d("ID_Clicado", "ID: " + transaction.getId());
                Log.d("Position", "Posição: " + transactions.get(position));
                Log.d("transaction", "Tran: " + transactions);

            });

            SimpleDateFormat dateInput = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            String dateFormated = "dd/MM/yyyy";
       /* try {
          //  Date date = dateInput.parse(transaction.getCreatedAt());
            SimpleDateFormat dateFinal = new SimpleDateFormat(dateFormated, Locale.getDefault());
            //String dateFinalFormated = dateFinal.format(date);
          //  itemView.txtDateSolicited.setText(dateFinalFormated);
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

            itemView.txtBookName.setText(transaction.getBookDetails().getName());
            itemView.txtStatusTrade.setText(transaction.getStatus());
            itemView.txtBuyerName.setText("Enviado para " + transaction.getBuyer().getFirstName() + " " + transaction.getBuyer().getLastName());
        }

    }

    @Override
    public int getItemCount() {
        return (int) transactions.stream().filter(transaction ->
                transaction.getStatus().equalsIgnoreCase("pendente")).count();
    }
}

