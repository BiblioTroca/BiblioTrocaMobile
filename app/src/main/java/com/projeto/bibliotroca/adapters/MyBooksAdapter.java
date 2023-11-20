package com.projeto.bibliotroca.adapters;

import com.projeto.bibliotroca.BookDetailsActivity;
import com.projeto.bibliotroca.EditBookActivity;
import com.projeto.bibliotroca.fragments.mybooks.MyBooksItemView;
import com.projeto.bibliotroca.R;
import com.projeto.bibliotroca.fragments.modal_variants.DeleteBookModalFragment;
import com.projeto.bibliotroca.models.BookCompleteDTO;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MyBooksAdapter extends RecyclerView.Adapter<MyBooksItemView> {

    Context context;

    private FragmentManager fragmentManager;

    private LayoutInflater inflater;

    List<BookCompleteDTO> books;

    public MyBooksAdapter(Context context, FragmentManager fragmentManager, List<BookCompleteDTO> books) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;
        this.books = books;
    }

    @NonNull
    @Override
    public MyBooksItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.mybook_item, parent, false);
        return new MyBooksItemView(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyBooksItemView itemView, int position) {
        itemView.btnOptionsB.setOnClickListener(view -> showPopupMenu(view,position));

        BookCompleteDTO book = books.get(position);

        itemView.txtBookAuthorCard.setText(book.getAuthor());
        itemView.txtBookTitleCard.setText(book.getName());
        itemView.txtBookCategoryCard.setText(book.getCategory());
        itemView.txtBookDate.setText(book.getCreatedAt(Instant.now().atZone(ZoneId.of("GMT-3")).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

        itemView.CardBook.setOnClickListener(event ->{
            Intent BookDetails = new Intent(context, BookDetailsActivity.class);
            BookDetails.putExtra("bookId",book.getId());
                context.startActivity(BookDetails);
        });

    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void showPopupMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(context, view);
        MenuInflater inflater = popupMenu.getMenuInflater();
        inflater.inflate(R.menu.context_menu, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_edit) {

                BookCompleteDTO clickedBook = books.get(position);


                Intent editBookI = new Intent(context, EditBookActivity.class);
                editBookI.putExtra("livro_id",clickedBook.getId());
                context.startActivity(editBookI);
                return true;

            }
            else if (itemId == R.id.menu_delete) {
                DeleteBookModalFragment modal = new DeleteBookModalFragment();
                modal.show(fragmentManager, "deleteBookModal");

                return true;
            } else {
                return false;
            }
        });
        popupMenu.show();
    }
}

