package com.projeto.bibliotroca;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import com.projeto.bibliotroca.fragments.exchange_library.ExchangeLibraryFragment;
import com.projeto.bibliotroca.fragments.mybooks.MyBooksFragment;
import com.projeto.bibliotroca.fragments.wishlist.WishlistFragment;
import com.projeto.bibliotroca.models.BookCompleteDTO;
import com.projeto.bibliotroca.models.BookSimpleDTO;
import com.projeto.bibliotroca.models.TransactionDTO;
import com.projeto.bibliotroca.services.BookService;
import com.projeto.bibliotroca.services.TransactionService;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity  {

    private Context context;

    private List<BookCompleteDTO> livros = new ArrayList<>();
    List<BookSimpleDTO> books = new ArrayList<>();

    List<TransactionDTO> transactions = new ArrayList<>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_fragment);

        ImageView profileImage = (ImageView) findViewById(R.id.profileImage);
        ConstraintLayout Cardbook = (ConstraintLayout) findViewById(R.id.Card_book);
        ConstraintLayout CardWish = (ConstraintLayout) findViewById(R.id.Card_wish);
        ConstraintLayout CardHist = (ConstraintLayout) findViewById(R.id.Card_Hist);
        ConstraintLayout CardTrades = (ConstraintLayout) findViewById(R.id.Card_trocas);
        ConstraintLayout CardLib = (ConstraintLayout) findViewById(R.id.Card_Lib);

        TextView amountBook = (TextView) findViewById(R.id.txtLivros);

        BookService bookService = new BookService();
        bookService.getListBookFull(livros);

        String amountBooks = "";
        if (livros.size() > 0) {
            amountBooks = livros.size() + " Livros";
        }
        amountBook.setText(amountBooks);

        bookService.getListBook(books);

        TransactionService transactionService = new TransactionService();
        transactionService.getListTransaction(transactions);

        TextView txtLib = findViewById(R.id.txtLib);

        String amountItems = "";
        if (books.size() > 0) {
            amountItems = +books.size() + " itens";
        }
        txtLib.setText(amountItems);
        TextView txtTradeAmountItems = findViewById(R.id.pendentes);

        String amountTrades = "";
        if (transactions.size() > 0) {
            amountTrades =  transactions.size() + " itens";
        }
        txtTradeAmountItems.setText(amountTrades);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(item -> {
                    int itemId = item.getItemId();
                    if (itemId == R.id.managerProfile) {
                        Intent profileIntent = new Intent(v.getContext(), ProfileActivity.class);
                        v.getContext().startActivity(profileIntent);
                        return true;
                    } else if (itemId == R.id.LeaveSection) {
                        Intent logout = new Intent(v.getContext(), HomeActivity.class);
                        v.getContext().startActivity(logout);
                        return true;
                    } else {
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        Cardbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, MyBooksFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();
            }
        });
        CardWish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, WishlistFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });
        CardHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3,HistoricFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });
        CardTrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3,PendingExchangeFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });
        CardLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager= getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainerView3, ExchangeLibraryFragment.class,null)
                        .setReorderingAllowed(true)
                        .addToBackStack("name")
                        .commit();

            }
        });
    }
}

