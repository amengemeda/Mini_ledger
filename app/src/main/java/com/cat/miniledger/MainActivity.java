package com.cat.miniledger;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView balanceView;
    private TextView totalIncomeView;
    private TextView totalExpenditureView;
    private String balance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        balanceView= findViewById(R.id.balance);
        totalIncomeView= findViewById(R.id.total_income);
        totalExpenditureView= findViewById(R.id.total_expenditure);
        DatabaseHelper db= new DatabaseHelper(this);
        balanceView.setText(db.getBalance());
        totalIncomeView.setText(db.getTotalIncome());
        totalExpenditureView.setText(db.getTotalExpenditure());
    }

    public void goToAddTransactionPage(View view) {
        Intent myIntent= new Intent(this, AddTransaction.class);
        startActivity(myIntent);
    }

    public void goToTransactionPage(View view) {
        Intent myIntent= new Intent(this, Transaction.class);
        startActivity(myIntent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        balanceView= findViewById(R.id.balance);
        totalIncomeView= findViewById(R.id.total_income);
        totalExpenditureView= findViewById(R.id.total_expenditure);
        DatabaseHelper db= new DatabaseHelper(this);
        balanceView.setText(db.getBalance()+ " Ksh");
        totalIncomeView.setText(db.getTotalIncome()+ " Ksh");
        totalExpenditureView.setText(db.getTotalExpenditure()+ " Ksh");
    }
}