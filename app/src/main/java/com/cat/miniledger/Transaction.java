package com.cat.miniledger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Transaction extends AppCompatActivity {
    private  ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        initialize();
    }
    public void initialize(){
        mListView = findViewById(R.id.listView);

        ArrayList<TransactionHandler> transactionsList = new ArrayList<>();

        DatabaseHelper db= new DatabaseHelper(this);
        Cursor data = db.getListContents();
        if (data.getCount()==0){
            Toast.makeText(this, "No transaction yet!", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                TransactionHandler transaction = new TransactionHandler(
                        data.getInt(0),
                        data.getDouble(1),
                        data.getString(2),
                        data.getString(3),
                        data.getString(4)
                );
                transactionsList.add(transaction);
            }
        }
        TransactionListAdapter transactionListAdapter= new TransactionListAdapter(this, R.layout.activity_transactions,transactionsList);
        mListView.setAdapter(transactionListAdapter);
        setOnClickListener();
    }
    private void setOnClickListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransactionHandler transaction = (TransactionHandler) mListView.getItemAtPosition(position);
                Intent editTransactionIntent= new Intent(getApplicationContext(),EditTransaction.class);
                editTransactionIntent.putExtra(TransactionHandler.TRANSACTION_EDIT_EXTRA, transaction.getTransactionId());
                startActivity(editTransactionIntent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initialize();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialize();
    }
}