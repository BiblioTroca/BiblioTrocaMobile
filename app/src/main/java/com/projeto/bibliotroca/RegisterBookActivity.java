package com.projeto.bibliotroca;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class RegisterBookActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
 void buildStyles() {
  getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary500));
 }


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

 public void handleCheckedRadioItem(View view) {
  ConstraintLayout radioItemGoodcondition = findViewById(R.id.selectableGoodCondition);
  ConstraintLayout radioItemNewcondition = findViewById(R.id.selectableNewCondition);
  ConstraintLayout radioItemOldcondition = findViewById(R.id.selectableUsedCondition);
  View radioCircleNew = findViewById(R.id.radioNewCondition);
  View radioCircleGood = findViewById(R.id.radioGoodCondition);
  View radioCircleBad = findViewById(R.id.radioBadCondition);


  view.setSelected(!view.isSelected());

  boolean isExistsRadioNew = radioItemNewcondition != null && radioCircleNew != null;
  boolean isExistsRadioGood = radioItemGoodcondition != null && radioCircleGood != null;
  boolean isExistsRadioBad = radioItemOldcondition != null && radioCircleBad != null;


  if (isExistsRadioNew) {
   if (view == radioItemNewcondition) {
    radioItemNewcondition.setSelected(true);
    radioCircleNew.setSelected(true);

    if(isExistsRadioGood) {
     radioItemGoodcondition.setSelected(false);
     radioCircleGood.setSelected(false);
    }

    if(isExistsRadioBad){
     radioItemOldcondition.setSelected(false);
     radioCircleBad.setSelected(false);
    };

    return;
   }

   radioItemNewcondition.setSelected(false);
   radioCircleNew.setSelected(false);

   if(isExistsRadioGood){
    radioItemGoodcondition.setSelected(true);
    radioCircleGood.setSelected(true);
   }
   if(isExistsRadioBad){
    radioItemOldcondition.setSelected(false);
    radioCircleBad.setSelected(false);
   }

  }
  if (isExistsRadioBad) {
   if (view == radioItemOldcondition) {
    assert radioItemNewcondition != null;
    radioItemNewcondition.setSelected(false);
    assert radioCircleNew != null;
    radioCircleNew.setSelected(false);

    if(isExistsRadioGood) {
     radioItemGoodcondition.setSelected(false);
     radioCircleGood.setSelected(false);
    }

    radioItemOldcondition.setSelected(true);
    radioCircleBad.setSelected(true);
    ;
  }
 }

}
}
