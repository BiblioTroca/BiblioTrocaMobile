package com.projeto.bibliotroca;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

 @Override
 protected void onCreate(@Nullable Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.register_book_layout);

  Spinner spinner = findViewById(R.id.spinner);
  ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(this, R.array.spinner_options, android.R.layout.simple_spinner_item);
  adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
  spinner.setAdapter(adapter);
  spinner.setOnItemSelectedListener(this);
 }

 @Override
 public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
  String text = parent.getItemAtPosition(position).toString();
  Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT).show();
 }

 @Override
 public void onNothingSelected(AdapterView<?> parent) {

 }
}
