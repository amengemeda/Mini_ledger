package com.cat.miniledger;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class EditTransaction extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private EditText editTransactionAmount;
    private Button editTransactionDate;
    private EditText editTransactionSource;
    private RadioButton editIncomeRadioButton;
    private RadioButton editExpenditureRadioButton;
    private int transactionId;
    TransactionHandler selectedTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_transaction);

        //initialize widgets
        initWidgets();
        checkForEditNote();

    }

    private void initWidgets()
    {
        this.editTransactionAmount = findViewById(R.id.editTransactionAmount);
        this.editTransactionDate = findViewById(R.id.editTransactionDate);
        this.editTransactionSource = findViewById(R.id.editTransactionSource);
        this.editIncomeRadioButton = findViewById(R.id.editIncomeRadioButton);
        this.editExpenditureRadioButton = findViewById(R.id.editExpenditureRadioButton);
    }
    public  TransactionHandler getTransactionForId(int transactionId){
        DatabaseHelper db= new DatabaseHelper(this);
        TransactionHandler transaction= db.getSpecificTransaction(transactionId);
        if (!transaction.getTransactionType().equals("")){
            return transaction;
        }else{
            return null;
        }

    }
    private void checkForEditNote()
    {
        Intent previousIntent = getIntent();

        int passedTransactionID = previousIntent.getIntExtra(TransactionHandler.TRANSACTION_EDIT_EXTRA, -1);
        this.transactionId= passedTransactionID;
        selectedTransaction = this.getTransactionForId(passedTransactionID);

        if (selectedTransaction != null)
        {
            this.editTransactionAmount.setText(String.valueOf(selectedTransaction.getTransactionAmount()));
            this.editTransactionDate.setText(selectedTransaction.reverseParseDateToddMMyyyy(selectedTransaction.getTransactionDate()));
            this.editTransactionSource.setText(selectedTransaction.getTransactionSource());
            if (selectedTransaction.getTransactionType().equals("Expense")){
                this.editIncomeRadioButton.setChecked(false);
                this.editExpenditureRadioButton.setChecked(true);
            }else if (selectedTransaction.getTransactionType().equals("Income")){
                this.editExpenditureRadioButton.setChecked(false);
                this.editIncomeRadioButton.setChecked(true);
            }
        }
        else
        {
            Toast toast= Toast.makeText(this,"Error occurred",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void editTransaction(View view) {
        double transactionAmount=  Double.parseDouble(this.editTransactionAmount.getText().toString());
        String transactionDate=  this.editTransactionDate.getText().toString();
        String transactionSource=  this.editTransactionSource.getText().toString();
        String transactionType= "";
        if(editIncomeRadioButton.isChecked()){
            transactionType = "Income";
        }else if(editExpenditureRadioButton.isChecked()){
            transactionType = "Expense";
        }

        DatabaseHelper db= new DatabaseHelper(this);
        boolean result= db.updateData(this.transactionId,transactionAmount,transactionType,transactionDate,transactionSource);
        if(result){
            Toast.makeText(this, "Transaction Edited Successfully", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Oops! something went wrong", Toast.LENGTH_SHORT).show();
        }
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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date= dayOfMonth + "/" + month + "/" + year;
        this.editTransactionDate.setText(date);
    }

    public void deleteTransaction(View view) {

        DatabaseHelper db= new DatabaseHelper(this);
        Log.d("To be deleted: ","Deleted: "+transactionId);
        boolean result= db.deleteData(transactionId);
        if (result){
            Toast.makeText(this, "Transaction deleted successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Oops something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}