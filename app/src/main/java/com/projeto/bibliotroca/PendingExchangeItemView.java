package com.projeto.bibliotroca;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PendingExchangeItemView extends RecyclerView.ViewHolder{

    public TextView txtBookName;
    public TextView txtStatusTrade;
    public TextView txtDateSolicited;
    public TextView txtBuyerName;

public TextView textView63;


    public PendingExchangeItemView(@NonNull View itemView) {
        super(itemView);

        textView63 = itemView.findViewById(R.id.txtBookNameTrades);

        txtBookName = itemView.findViewById(R.id.txtBookNameTrades);
        txtStatusTrade = itemView.findViewById(R.id.txtStatusTrade);
        txtDateSolicited = itemView.findViewById(R.id.txtDateSolicitedTrades);
        txtBuyerName = itemView.findViewById(R.id.txtBuyerNameTrade);


    }

}
