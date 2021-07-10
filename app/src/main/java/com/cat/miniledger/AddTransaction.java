package com.cat.miniledger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddTransaction extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText transactionAmount;
    private Button transactionDate;
    private EditText transactionSource;
    private RadioButton incomeRadioButton;
    private RadioButton expenditureRadioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        transactionAmount= findViewById(R.id.transactionAmount);
        transactionDate= findViewById(R.id.transactionDate);
        transactionSource= findViewById(R.id.transactionSource);
        incomeRadioButton= findViewById(R.id.incomeRadioButton);
        expenditureRadioButton= findViewById(R.id.expenditureRadioButton);
    }

    public void addTransaction(View view) {
        double transactionAmount=  Double.parseDouble(this.transactionAmount.getText().toString());
        String transactionDate=  this.transactionDate.getText().toString();
        String transactionSource=  this.transactionSource.getText().toString();
        String transactionType= "";
        if(incomeRadioButton.isChecked()){
            transactionType = "Income";
        }else if(expenditureRadioButton.isChecked()){
            transactionType = "Expense";
        }
//        TransactionHandler transaction = new TransactionHandler(transactionAmount,transactionDate,transactionSource,"Earning");
        DatabaseHelper db= new DatabaseHelper(this);
        boolean result= db.addData(transactionAmount,transactionType,transactionDate,transactionSource);
        if(result){
            Toast.makeText(this, "Transaction added Successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date= dayOfMonth + "/" + month + "/" + year;
        this.transactionDate.setText(date);
    }

    public void openDatePicker(View view) {
        DatePickerDialog datePickerDialog= new DatePickerDialog(this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }
}