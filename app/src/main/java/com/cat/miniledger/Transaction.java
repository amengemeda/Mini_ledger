package com.cat.miniledger;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Transaction extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ListView mListView = findViewById(R.id.listView);

        ArrayList<TransactionHandler> transactionsList = new ArrayList<>();

        DatabaseHelper db= new DatabaseHelper(this);
        Cursor data = db.getListContents();
        if (data.getCount()==0){
            Toast.makeText(this, "No transaction yet!", Toast.LENGTH_SHORT).show();
        }else{
            while(data.moveToNext()){
                TransactionHandler transaction = new TransactionHandler(
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
    }

}